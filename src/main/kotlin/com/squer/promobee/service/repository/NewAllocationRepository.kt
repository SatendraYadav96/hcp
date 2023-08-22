package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.*
import com.squer.promobee.controller.dto.*
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


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

    fun isPlanApprovedOrSubmitLock(month: String, year: String):  ResponseEntity<out Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        try {

            var now = LocalDate.now()

            var day = now.dayOfMonth

            var dayValue = day.toInt()

            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK.id)


            var bmPlanLockValue = sqlSessionFactory.openSession().selectOne<SystemProperties>("SystemPropertiesMapper.checkPlanLock",data)


            var sypValue = bmPlanLockValue.value!!.toInt()

            if(dayValue >= sypValue ){

                return ResponseEntity.ok("Plan is locked ")
                print("Plan is locked")


            }


        }catch (e: Exception){

            e.printStackTrace()

        }



        return ResponseEntity.ok("plan is unlocked and BM can allocate")
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

                    val expiryDateInv: Date? = inventory.expiryDate

                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    val invExpiry: String = sdf.format(expiryDateInv)

                    var cutDate = cutoffDate.toString()

                    data0.put("sampleId", inventory.item!!.id)
                    data0.put("cutoffday", cutDate)
                    data0.put("invExpiry", invExpiry)

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


    fun getTeamForCommonAllocation(ccmId: String): List<CommonAllocationTeamDTO>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User



        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("ccmId",ccmId)

        return sqlSessionFactory.openSession().selectList<CommonAllocationTeamDTO>("TeamMapper.getTeamForCommonAllocation",data)




    }


    fun getQuantityAllocatedOfUserToItem(userId: String, userDesgId: String, inventoryId: String, month: Int, year: Int, isSpecialDispatch: Int): List<DesignationWiseQuantityAllocatedDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var quantityDispatch = mutableListOf<DesignationWiseQuantityAllocatedDTO>()

        var i = 0

        try {
            if(user.userDesignation!!.id == UserRoleEnum.BEX_ID.id){
                var data: MutableMap<String, Any> = mutableMapOf()

                data.put("month",month)
                data.put("year",year)
                data.put("inventoryId",inventoryId)
                data.put("isSpecialDispatch",isSpecialDispatch)

                quantityDispatch =  sqlSessionFactory.openSession().selectList<DesignationWiseQuantityAllocatedDTO>("DispatchDetailMapper.getQuantityAllocatedOfUserToItemForBex",data)

              //  quantityDispatch = quantityDispatch1[quantityDispatch]!!


            }else {
                var data: MutableMap<String, Any> = mutableMapOf()

                data.put("month",month)
                data.put("year",year)
                data.put("inventoryId",inventoryId)
                data.put("isSpecialDispatch",isSpecialDispatch)
                data.put("userId",user.id)

                    quantityDispatch =  sqlSessionFactory.openSession().selectList<DesignationWiseQuantityAllocatedDTO>("DispatchDetailMapper.getQuantityAllocatedOfUserToItem",data)

               // quantityDispatch = quantityDispatch1[quantityDispatch]!!
            }




        }catch (e : Exception){
            e.printStackTrace()
        }

        return quantityDispatch
    }






     fun getTeamForDifferentialAllocation(planId: String, teamId: String, inventoryId: String): List<AllocationDataTeamPopupDetailsDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

         var teamPopupDetails = mutableListOf<AllocationDataTeamPopupDetailsDTO>()

         if(user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id ) {
             var data: MutableMap<String, Any> = mutableMapOf()

             data.put("PlanID",planId)
             data.put("TeamID",teamId)
             data.put("InventoryID",inventoryId)

             teamPopupDetails = sqlSessionFactory.openSession().selectList<AllocationDataTeamPopupDetailsDTO>("TeamMapper.getTeamForDifferentialAllocation",data)


         } else {
             var data: MutableMap<String, Any> = mutableMapOf()

             data.put("PlanID",planId)
             data.put("TeamID",teamId)
             data.put("InventoryID",inventoryId)

             teamPopupDetails = sqlSessionFactory.openSession().selectList<AllocationDataTeamPopupDetailsDTO>("TeamMapper.getTeamForDifferentialAllocation",data)
         }

         return teamPopupDetails

    }



     fun saveCommonAllocation(saveAlloc: List<saveCommonAllocationDTO>) {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

         var data: MutableMap<String, Any> = mutableMapOf()

         var recipient = mutableListOf<Recipient>()

             var i = 0

         saveAlloc.forEach {

                 var data1: kotlin.collections.MutableMap<kotlin.String, kotlin.Any> = kotlin.collections.mutableMapOf()

                 saveAlloc[i].designationId?.let { it1 -> data1.put("designationId", it1) }
                 saveAlloc[i].teamId?.let { it1 -> data1.put("teamId", it1) }

                 recipient = sqlSessionFactory.openSession()
                     .selectList<com.squer.promobee.service.repository.domain.Recipient>("RecipientMapper.getRecipientToSaveAllocation", data1)


                 recipient.forEach {it ->

                     var dispatchDetail = com.squer.promobee.service.repository.domain.DispatchDetail()

                     var data: kotlin.collections.MutableMap<kotlin.String, kotlin.Any> =
                         kotlin.collections.mutableMapOf()

                     data.put("id", java.util.UUID.randomUUID().toString())
                     saveAlloc[i].dispatchPlanId?.let { it1 -> data.put("planId", it1) }
                     saveAlloc[i].inventoryId?.let { it1 -> data.put("inventoryId", it1) }
                     data.put("recipientId", it.id)
                     saveAlloc[i].quantity?.let { it1 -> data.put("qtyDispatch", it1) }
                     data.put("quarterlyPlanId", "00000000-0000-0000-0000-000000000000")
                     data.put("detailStatus", com.squer.promobee.api.v1.enums.DispatchDetailStatusEnum.ALLOCATED.id)
                     data.put("createdBy", user.id)
                     data.put("updatedBy", user.id)

                     sqlSessionFactory.openSession().insert("DispatchDetailMapper.saveCommonAllocation", data)


                     var data2: kotlin.collections.MutableMap<kotlin.String, kotlin.Any> =
                         kotlin.collections.mutableMapOf()


                     saveAlloc[i].inventoryId?.let { it1 -> data2.put("id", it1) }

                     var inv = sqlSessionFactory.openSession()
                         .selectOne<com.squer.promobee.service.repository.domain.Inventory>("InventoryMapper.getInventoryByIdForInvoicing", data2)

                     var data3: kotlin.collections.MutableMap<kotlin.String, kotlin.Any> =
                         kotlin.collections.mutableMapOf()

                     var invAllocatedQty = inv.qtyAllocated?.plus(saveAlloc[i].quantity!!)

                     data3.put("id", inv.id)
                     data3.put("qtyAllocated", invAllocatedQty!!)
                     data3.put("updatedBy", user.id)

                     sqlSessionFactory.openSession().update("InventoryMapper.saveCommonAllocation", data3)




                 }

             i++

             }





             }



    fun saveDifferentialAllocation(saveAlloc: List<saveDifferentialAllocation>) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        var dispatchDetails = DispatchDetail()

        saveAlloc.forEach { it ->
            data.put("id", UUID.randomUUID().toString())
            data.put("planId",it.dispatchPlanId!!)
            data.put("inventoryId",it.inventoryId!!)
            data.put("recipientId",it.recipientId!!)
            data.put("qtyDispatch",it.quantity!!)
            data.put("quarterlyPlanId","00000000-0000-0000-0000-000000000000")
            data.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)

            sqlSessionFactory.openSession().insert("DispatchDetailMapper.saveCommonAllocation",data)


            var data2: MutableMap<String, Any> = mutableMapOf()


            data2.put("id", it.inventoryId!!)

            var inv = sqlSessionFactory.openSession()
                .selectOne<Inventory>("InventoryMapper.getInventoryByIdForInvoicing", data2)

            var data3: MutableMap<String, Any> = mutableMapOf()

            var invAllocatedQty = inv.qtyAllocated?.plus(it.quantity!!)

            data3.put("id", inv.id)
            data3.put("qtyAllocated", invAllocatedQty!!)
            data3.put("updatedBy", user.id)

            sqlSessionFactory.openSession().update("InventoryMapper.saveCommonAllocation", data3)



        }



    }


    fun submitMonthlyAllocation(alloc : submitAllocationDTO){
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        try {
            var data: MutableMap<String, Any> = mutableMapOf()
            var plan = DispatchPlan()

            data.put("month",alloc.month!!)
            data.put("year",alloc.year!!)
            data.put("owner",user.id)

            plan = sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanForAllocation",data)

            var data0: MutableMap<String, Any> = mutableMapOf()

            data0.put("id",plan.id)
            data0.put("planStatus",AllocationStatusEnum.SUBMIT.id)
            data0.put("invoiceStatus", DispatchPlanInvoiceStatus.NOT_INITIATED.id)
            data0.put("updatedBy",user.id)

            sqlSessionFactory.openSession().update("DispatchPlanMapper.submitMonthlyAllocation",data0)


            var brandslist = mutableListOf<BrandManager>()

            var data1: MutableMap<String, Any> = mutableMapOf()
            data1.put("id",user.id)

            brandslist = sqlSessionFactory.openSession().selectList<BrandManager>("BrandManagerMapper.submitMonthlyAllocation",data1)

            brandslist.forEach {it ->
                var dpbt = DispatchPlanBrand()

                var data2: MutableMap<String, Any> = mutableMapOf()

                data2.put("id",UUID.randomUUID().toString())
                data2.put("dipId",plan.id)
                data2.put("brdId",it.brandId!!.id)
                data2.put("dipOwnerId",plan.owner!!.id)
                data2.put("createdBy",user.id)
                data2.put("updatedBy",user.id)

                sqlSessionFactory.openSession().insert("DispatchPlanBrandMapper.submitMonthlyAllocation",data2)
            }


            var approvalChainTransaction = ApprovalChainTransaction()
            var data3: MutableMap<String, Any> = mutableMapOf()
            data3.put("id",plan.id)

            approvalChainTransaction = sqlSessionFactory.openSession().selectOne("ApprovalChainTransactionMapper.getApprovalChainById",data3)

            if(approvalChainTransaction != null){
                var data4: MutableMap<String, Any> = mutableMapOf()

                data4.put("owner",plan.id)
                data4.put("apiStatus",ApprovalStatusEnum.PENDING_APPROVAL.id)
                data4.put("updatedBy",user.id)

                sqlSessionFactory.openSession().update("ApprovalChainTransactionMapper.updateSaveMonthlyToSpecial",data4)

            }else {
                var data5: MutableMap<String, Any> = mutableMapOf()

                data5.put("id",UUID.randomUUID().toString())
                data5.put("owner",plan.id)
                data5.put("designation",UserRoleEnum.BEX_ID.id)
                data5.put("apiStatus",ApprovalStatusEnum.PENDING_APPROVAL.id)
                data5.put("createdBy",user.id)
                data5.put("updatedBy",user.id)

                sqlSessionFactory.openSession().update("ApprovalChainTransactionMapper.insertSaveMonthlyToSpecial",data5)
            }


        }
        catch (e: Exception){

        }


    }



    // SPECIAL ALLOCATION

    fun createSpecialPlan(alloc: CreateAllocationDTO): MutableList<AllocationInventoryDetailsWithCostCenterDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User



            var plan = DispatchPlan()

            var data: MutableMap<String, Any> = mutableMapOf()

            var planId = UUID.randomUUID().toString()

            data.put("id", planId)
            data.put("owner", user.id)
            data.put("month", alloc.month!!)
            data.put("year", alloc.year!!)
            data.put("planStatus", AllocationStatusEnum.DRAFT.id)
            data.put("isSpecial", 1)
            data.put("remarks", alloc.name!!)
            data.put("createdBy", user.id)
            data.put("updatedBy", user.id)
            data.put("invoiceStatus", DispatchPlanInvoiceStatus.NOT_INITIATED.id)
            data.put("isVirtual", 0)

            sqlSessionFactory.openSession().insert("DispatchPlanMapper.insertSpecialPlanForAllocation", data)

            var diu = mutableListOf<DispatchPlanUtilization>()

            var data0: MutableMap<String, Any> = mutableMapOf()

            data0.put("dipId", planId)

            diu = sqlSessionFactory.openSession()
                .selectList<DispatchPlanUtilization>("DispatchPlanUtilizationMapper.createSpecialAllocation", data0)

            diu.forEach {

                var data1: MutableMap<String, Any> = mutableMapOf()

                data1.put("dipId", planId)

                sqlSessionFactory.openSession()
                    .delete("DispatchPlanUtilizationMapper.deleteSpecialDispatchPlanUtilization", data1)

            }

            diu.forEach {
                var du = DispatchPlanUtilization()
                var data2: MutableMap<String, Any> = mutableMapOf()

                data2.put("id", UUID.randomUUID().toString())
                data2.put("dipId", planId)
                data2.put("month", alloc.month!!)
                data2.put("createdBy", user.id)
                data2.put("updatedBy", user.id)

                sqlSessionFactory.openSession()
                    .insert("DispatchPlanUtilizationMapper.insertSpecialDispatchPlanUtilization", data2)


            }

            var allocationInventoryDetails = mutableListOf<AllocationInventoryDetailsWithCostCenterDTO>()

            var data3: MutableMap<String, Any> = mutableMapOf()

            data3.put("UserID", user.id)
            data3.put("PlanID", plan.id)


            allocationInventoryDetails = sqlSessionFactory.openSession()
                .selectList<AllocationInventoryDetailsWithCostCenterDTO>(
                    "ReportMapper.allocationDetailsWithCostCenter",
                    data3
                )
        //var i = 0
        //var inv = allocationInventoryDetails.sortBy { allocationInventoryDetails[i].qtyAllocated!!.dec() }


          return allocationInventoryDetails



    }


    fun editSpecialPlan(planId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var plan = DispatchPlan()

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id", planId)

        plan = sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanCount",data)

        var allocationInventoryDetails = mutableListOf<AllocationInventoryDetailsWithCostCenterDTO>()



        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("UserID", user.id)
        data0.put("PlanID", plan.id)


        allocationInventoryDetails = sqlSessionFactory.openSession()
            .selectList<AllocationInventoryDetailsWithCostCenterDTO>(
                "ReportMapper.allocationDetailsWithCostCenter",
                data0
            )

        val sortedAllocationDetails = allocationInventoryDetails.sortedByDescending { it.qtyAllocated }.toMutableList()

        return sortedAllocationDetails



    }


    fun searchSpecialPlan(month: Int, year: Int, status: String, remark: String): MutableList<DispatchPlan> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var plan = mutableListOf<DispatchPlan>()
        if(remark.isNullOrEmpty()){
            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("month",month)
            data.put("year",year)
            data.put("status",status)

            plan = sqlSessionFactory.openSession().selectList("DispatchPlanMapper.searchSpecialPlanWithoutRemarks",data)

        } else {
            var data0: MutableMap<String, Any> = mutableMapOf()

            data0.put("month",month)
            data0.put("year",year)
            data0.put("status",status)

            plan = sqlSessionFactory.openSession().selectList("DispatchPlanMapper.searchSpecialPlanWithRemarks",data0)
        }


        return plan


    }


















}


















