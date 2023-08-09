package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.*
import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.SampleFifoDetailsModelDTO
import com.squer.promobee.controller.dto.TseListDTO
import com.squer.promobee.controller.dto.UserDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.*
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.time.Duration.Companion.days


@Repository
class NewAllocationRepository(
    securityUtility: SecurityUtility,
): BaseRepository<Users>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    @Autowired
    lateinit var inventoryRepository: InventoryRepository

    @Autowired
    lateinit var masterRepository: MasterRepository


    fun getTseList(id: String): List<TseListDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("bmID",id)

        return sqlSessionFactory.openSession().selectList<TseListDTO>("BrandManagerMapper.getTseList",data)

    }

    fun assignTse(id: String): List<TseListDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()



        data.put("id",UUID.randomUUID().toString())
        data.put("bmID",user.id)
        data.put("tseID",id)

        sqlSessionFactory.openSession().insert("BrandManagerMapper.assignTse",data)


        var data0: MutableMap<String, Any> = mutableMapOf()
        data0.put("bmID",user.id)


        return sqlSessionFactory.openSession().selectList<TseListDTO>("BrandManagerMapper.getTseList",data0)

    }


    fun unAssignTse(id: String): List<TseListDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()



        data.put("id",id)


        sqlSessionFactory.openSession().delete("BrandManagerMapper.unAssignTse",data)


        var data0: MutableMap<String, Any> = mutableMapOf()
        data0.put("bmID",user.id)


        return sqlSessionFactory.openSession().selectList<TseListDTO>("BrandManagerMapper.getTseList",data0)

    }


    fun getBrandManagerForTse(id: String): List<UserDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()



        data.put("tseId",id)


        return sqlSessionFactory.openSession().selectList<UserDTO>("BrandManagerMapper.getBrandManagerForTse",data)




    }


    fun createMonthlyPlan(yearMonth: Long): List<AllocationInventoryDetailsWithCostCenterDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        val month = (yearMonth % 100).toInt()
        val year = (yearMonth / 100).toInt()



        var tseID =  getTseList(user.id)

        var allocationInventoryDetails = mutableListOf<AllocationInventoryDetailsWithCostCenterDTO>()

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("month",month)
        data.put("year",year)
        data.put("owner",user.id)

        var plan = sqlSessionFactory.openSession().selectOne<DispatchPlan>("DispatchPlanMapper.getDispatchPlanForAllocation",data)

        var isRbmOrNsm = false;

        if(user.userDesignation?.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id || user.userDesignation?.id == UserRoleEnum.NATIONAL_SALES_MANAGER_ID.id){
            isRbmOrNsm = true;
        }

        var data0: MutableMap<String, Any> = mutableMapOf()
        user.userDesignation?.let { data0.put("id", it.id) }

        var ulv = sqlSessionFactory.openSession().select("UserDesignationMapper.userDesignationForAllocation",data0)

        if(plan == null){
                var dispatchPlan = DispatchPlan()

            var data1: MutableMap<String, Any> = mutableMapOf()

            var dipID = UUID.randomUUID().toString()

            data1.put("id",dipID)
            data1.put("owner",user.id)
            data1.put("month",month)
            data1.put("year",year)
            data1.put("planStatus",AllocationStatusEnum.DRAFT.id)
            data1.put("isSpecial",0)
            data1.put("createdBy",user.id)
            data1.put("updatedBy",user.id)
            data1.put("invoiceStatus", DispatchPlanInvoiceStatus.NOT_INITIATED.id)

            sqlSessionFactory.openSession().insert("DispatchPlanMapper.insertPlanForAllocation",data1)

        }

        var BmPlanId = mutableListOf<DispatchDetail>()
        if(isRbmOrNsm) {

            var data2: MutableMap<String, Any> = mutableMapOf()

            user.userRecipientId?.let { data2.put("rbmId", it) }
            data2.put("month",month)
            data2.put("year",year)

             BmPlanId = sqlSessionFactory.openSession().selectList("DispatchDetailMapper.getDispatchDetailsForAllocation",data2)

            var i = 0

            var rbmdetails =  mutableListOf<DispatchDetail>()
            var status = SystemLov()
            var BMPlan = DispatchPlan()
            BmPlanId.forEach {

                var data3: MutableMap<String, Any> = mutableMapOf()
                data3.put("planId",plan.id)
                BmPlanId[i].inventoryId?.let { it1 -> data3.put("invId", it1) }


                rbmdetails = sqlSessionFactory.openSession().selectList("DispatchDetailMapper.rbmDetails",data3)

                BMPlan = sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanById",BmPlanId[i].planId)

                if(BMPlan.isSpecial == 0 && BMPlan.planStatus?.id == AllocationStatusEnum.APPROVED.id){
                    if(rbmdetails.isNullOrEmpty()){
                        var detail = DispatchDetail()

                        var data4: MutableMap<String, Any> = mutableMapOf()
                        var didID = UUID.randomUUID().toString()

                        data4.put("id",didID)
                        data4.put("planId",plan.id)
                        BmPlanId[i].inventoryId?.let { it1 -> data4.put("inventoryId", it1) }
                        BmPlanId[i].recipientId?.let { it1 -> data4.put("recipientId", it1) }
                        BmPlanId[i].qtyDispatch?.let { it1 -> data4.put("qtyDispatch", it1) }
                        BmPlanId[i].quarterlyPlanId?.let { it1 -> data4.put("quarterlyPlanId", it1) }
                        BmPlanId[i].detailStatus?.let { it1 -> data4.put("detailStatus", it1?.id) }
                        data4.put("createdBy",user.id)
                        data4.put("updatedBy",user.id)
                        BmPlanId[i].categoryId?.let { it1 -> data4.put("categoryId", it1?.id) }

                        sqlSessionFactory.openSession().insert("DispatchDetailMapper.dispatchDetailAllocationInsert",data4)


                        var data5: MutableMap<String, Any> = mutableMapOf()

                        data5.put("id",didID)
                        data5.put("updatedBy",user.id)

                        sqlSessionFactory.openSession().update("DispatchDetailMapper.dispatchDetailAllocationUpdate",data5)






                    }
                }




            }





        }

        if(isRbmOrNsm){

            var data6: MutableMap<String, Any> = mutableMapOf()

            data6.put("UserID",user.id)
            data6.put("RBMPlanID",plan.id)


            allocationInventoryDetails = sqlSessionFactory.openSession().selectList<AllocationInventoryDetailsWithCostCenterDTO>("ReportMapper.allocationDetailsOfRbm",data6)
        }

        else{

            var data7: MutableMap<String, Any> = mutableMapOf()

            data7.put("UserID",user.id)
            data7.put("PlanID",plan.id)


            allocationInventoryDetails = sqlSessionFactory.openSession().selectList<AllocationInventoryDetailsWithCostCenterDTO>("ReportMapper.allocationDetailsWithCostCenter",data7)

        }

        val monthString = month.toString()
        val yearString = year.toString()

        var planCheck = isPlanApprovedOrSubmitLock(monthString ,yearString)


        return allocationInventoryDetails






    }

    fun isPlanApprovedOrSubmitLock(month: String, year: String) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var allocationStatus = ""
        var allocationInvoiceStatus = ""

        try {

            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("type","MONTHLY_PLAN_STATUS")
            var allocationTypes = sqlSessionFactory.openSession().selectList<SystemLov>("SystemLovMapper.allocationTypes",data)


            var data0: MutableMap<String, Any> = mutableMapOf()
            data0.put("month",month)
            data0.put("year",year)
            data0.put("owner",user.id)

            var plan = sqlSessionFactory.openSession().selectOne<DispatchPlan>("DispatchPlanMapper.getDispatchPlanForAllocation",data0)


            var data1: MutableMap<String, Any> = mutableMapOf()
            plan.planStatus?.let { data1.put("id", it.id) }


             var allocationStatus1 =   sqlSessionFactory.openSession().selectOne<SystemLov>("SystemLovMapper.allocationStatus",data1)

            allocationStatus = allocationStatus1.name.toString()

            var data2: MutableMap<String, Any> = mutableMapOf()
            plan.owner?.let { data2.put("ownerId", it.id) }

            var  api = sqlSessionFactory.openSession().selectOne<ApprovalChainTransaction>("ApprovalChainTransactionMapper.apiAllocation",data2)

            if(api != null){

                allocationStatus += api.comments

            }

            var data3: MutableMap<String, Any> = mutableMapOf()
            plan.invoiceStatus?.let { data3.put("id", it.id) }

            var allocationInvoiceStatus1 = sqlSessionFactory.openSession().selectOne<SystemLov>("SystemLovMapper.allocationInvoiceStatus",data3)

            allocationInvoiceStatus = allocationInvoiceStatus1.name.toString()

            var data4: MutableMap<String, Any> = mutableMapOf()
          data4.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK_OF_MONTH.id)

            var monthToSubtract = sqlSessionFactory.openSession().selectOne<SystemProperties>("SystemPropertiesMapper.monthToSubtract",data4)

            var monthToSubtract1 = monthToSubtract.value?.toInt()

            var now = LocalDate.now()
            var firstDayOfMonth = LocalDate.now().withDayOfMonth( 1 )
            var dayToAdd = 0
            var dayToAdd1 = 0
            var dayToAdd2 = 0
            var isPlanSubmit =  true
            var AllocationStatus =  ""
            var AllocationInvoiceStatus =  ""


            if(plan!= null) {

                if(plan.planStatus?.id == AllocationEnum.APPROVED.id){
                    isPlanSubmit = true
                    AllocationStatus =  allocationStatus
                    AllocationInvoiceStatus =  allocationInvoiceStatus
                    println("Plan Approved")
                }
                if(plan.planStatus?.id == AllocationEnum.SUBMIT.id){
                    isPlanSubmit = true
                    AllocationStatus =  allocationStatus
                    AllocationInvoiceStatus =  allocationInvoiceStatus
                    println("Plan Submit")
                }
                if(plan.planStatus?.id == AllocationEnum.DRAFT.id){

                    var data5: MutableMap<String, Any> = mutableMapOf()
                    data5.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK.id)

                    var dayToAdds = sqlSessionFactory.openSession().selectOne<SystemProperties>("SystemPropertiesMapper.dayToAddDraft",data5)

                    dayToAdd = dayToAdds.value!!.toInt()


                }

                if(plan.planStatus?.id == AllocationEnum.UNLOCK.id){

                    var data6: MutableMap<String, Any> = mutableMapOf()
                    data6.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK.id)

                    var dayToAddBm = sqlSessionFactory.openSession().selectOne<SystemProperties>("SystemPropertiesMapper.dayToAddDraft",data6)

                    dayToAdd1 = dayToAddBm.value!!.toInt()


                    var data7: MutableMap<String, Any> = mutableMapOf()
                    data7.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK.id)

                    var dayToAddBex = sqlSessionFactory.openSession().selectOne<SystemProperties>("SystemPropertiesMapper.dayToAddDraft",data7)


                    dayToAdd2 = dayToAddBex.value!!.toInt()

                    dayToAdd = dayToAdd1 + dayToAdd2

                }




            }
            else{
                var data8: MutableMap<String, Any> = mutableMapOf()
                data8.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK.id)

                var dayToAdds = sqlSessionFactory.openSession().selectOne<SystemProperties>("SystemPropertiesMapper.dayToAddDraft",data8)

                dayToAdd = dayToAdds.value!!.toInt()
            }

            val targetDate = monthToSubtract1?.let { firstDayOfMonth.minusMonths(it.toLong()).plusDays((dayToAdd - 1).toLong()) }

            var isPlanLock = targetDate!!.isBefore(now)

            if(isPlanLock){
                isPlanSubmit = true
                AllocationStatus =  allocationStatus
                AllocationInvoiceStatus =  allocationInvoiceStatus
                println("Plan Lock")

            }

            var nYear = year.toInt()
            var nMonth = month.toInt()
            val tempFutureCheckActual = LocalDate.of(nYear, nMonth, 1)
            val tempFutureCheckNow = LocalDate.now().withDayOfMonth(1)



            var futureMonthCheck = 2

            var futureMonth = futureMonthCheck.days

            val monthsDifference = ChronoUnit.MONTHS.between(tempFutureCheckNow, tempFutureCheckActual)

            val monthsDifferenceInt = monthsDifference.toInt()


            if(monthsDifferenceInt >= futureMonthCheck ){
                isPlanSubmit = true
                AllocationStatus =  ""
                AllocationInvoiceStatus =  ""
                println("Future Plan Lock")
            } else{
                isPlanSubmit = false
                AllocationStatus =  allocationStatus
                AllocationInvoiceStatus = allocationInvoiceStatus

            }




        }catch (e: Exception){
            e.printStackTrace()
        }


    }


    fun getcheckforsampleFifocheckpopup(planId: String, inventoryId: String, isItem: Int): ResponseEntity<out Any> {

        try {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

            var isrbm = false

            if (user.userDesignation?.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id || user.userDesignation?.id == UserRoleEnum.NATIONAL_SALES_MANAGER_ID.id) {
                isrbm = true;
            }

            var sample = SampleMaster()
            var inventory = inventoryRepository.getInventoryById(inventoryId)

            var fifoDetails = mutableListOf<SampleFifoDetailsModelDTO>()

            sample = masterRepository.getSampleById(inventory.item!!.id)

            if (!isrbm) {
                if (sample != null) {
                    var data: MutableMap<String, Any> = mutableMapOf()
                    data.put("itcId", inventory.categoryId!!.id)
                    var itc = sqlSessionFactory.openSession()
                        .selectOne<ItemCategoryMaster>("ItemCategoryMasterMapper.getItemCategoryById", data)

                    val cutoffDate = LocalDate.now().plusDays(itc.cutOffBeforeDays!!.toLong())

                    var data0: MutableMap<String, Any> = mutableMapOf()

                    data0.put("sampleId", inventory.item!!.id)
                    data0.put("cutoffday", cutoffDate)
                    data0.put("invExpiry", inventory.expiryDate.toString())

                    var inv = sqlSessionFactory.openSession()
                        .selectList<Inventory>("InventoryMapper.getInventoryForFifo", data0)

                    var invCount = inv.count()

                    var i = 0

                    if (invCount >= 1) {
                        inv.forEach {
                            var sfd = SampleFifoDetailsModelDTO()

                            sfd.inventoryId = inv[i].id
                            sfd.poNo = inv[i].poNo
                            sfd.expiryDate = inv[i].expiryDate.toString()
                            sfd.expiryDt = inv[i].expiryDate.toString().toShort().toString()
                            sfd.balance =
                                (inv[i].qtyReceived!! - inv[i].qtyAllocated!! - inv[i].qtyDispatched!!).toString()
                            sfd.batchNo = inv[i].batchNo
                            fifoDetails.add(sfd)

                            i++

                        }

                        return ResponseEntity.ok(mapOf("listdata" to fifoDetails, "status" to false))
                    } else {
                        return ResponseEntity.ok(mapOf("listdata" to fifoDetails, "status" to false))
                    }


                } else {
                    return ResponseEntity.ok(mapOf("listdata" to fifoDetails, "status" to true))
                }
            } else {
                return ResponseEntity.ok(mapOf("listdata" to fifoDetails, "status" to true))
            }


        } catch(e: Exception){
            return ResponseEntity.ok("This line item is not sample!")
        }



    }












}