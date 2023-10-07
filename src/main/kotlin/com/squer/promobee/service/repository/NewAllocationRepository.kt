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
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
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

    fun assignTse(id: String) {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data1: MutableMap<String, Any> = mutableMapOf()

        data1.put("tseID",id)
        data1.put("bmID",user.id)

        sqlSessionFactory.openSession().delete("BrandManagerMapper.deleteTse",data1)



        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",UUID.randomUUID().toString())
        data.put("bmID",user.id)
        data.put("tseID",id)

        sqlSessionFactory.openSession().insert("BrandManagerMapper.assignTse",data)


//        var data0: MutableMap<String, Any> = mutableMapOf()
//        data0.put("bmID",user.id)
//
//
//        return sqlSessionFactory.openSession().selectList<TseListDTO>("BrandManagerMapper.getTseList",data0)

    }


    fun unAssignTse(id: String) {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()



        data.put("id",id)


        sqlSessionFactory.openSession().delete("BrandManagerMapper.unAssignTse",data)


//        var data0: MutableMap<String, Any> = mutableMapOf()
//        data0.put("bmID",user.id)
//
//
//        return sqlSessionFactory.openSession().selectList<TseListDTO>("BrandManagerMapper.getTseList",data0)

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


    fun getQuantityAllocatedOfUserToItem(userId: String, inventoryId: String, month: Int, year: Int, isSpecialDispatch: Int): List<DesignationWiseQuantityAllocatedDTO> {

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






    fun getQuantityAllocatedDifferentialRecipient(planId: String,inventoryId: String, recipientId: String): List<DifferentialRecipientAllocationDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var quantityDispatch = mutableListOf<DifferentialRecipientAllocationDTO>()

        var i = 0

        try {

                var data: MutableMap<String, Any> = mutableMapOf()


                data.put("planId",planId)
                data.put("inventoryId",inventoryId)
                data.put("recipientId",recipientId)


                quantityDispatch =  sqlSessionFactory.openSession().selectList<DifferentialRecipientAllocationDTO>("DispatchDetailMapper.getQuantityAllocatedDifferentialRecipient",data)


            }
        catch (e : Exception) {
            e.printStackTrace()
        }

        return quantityDispatch
    }






    fun getTeamForDifferentialAllocation(planId: String, teamId: String, inventoryId: String): List<AllocationDifferentialRecipientDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

         var teamPopupDetails = mutableListOf<AllocationDifferentialRecipientDTO>()

         if(user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id ) {
             var data: MutableMap<String, Any> = mutableMapOf()

             data.put("PlanID",planId)
             data.put("TeamID",teamId)
             data.put("InventoryID",inventoryId)

             teamPopupDetails = sqlSessionFactory.openSession().selectList<AllocationDifferentialRecipientDTO>("TeamMapper.getTeamForDifferentialAllocation",data)


         } else {
             var data: MutableMap<String, Any> = mutableMapOf()

             data.put("PlanID",planId)
             data.put("TeamID",teamId)
             data.put("InventoryID",inventoryId)

             teamPopupDetails = sqlSessionFactory.openSession().selectList<AllocationDifferentialRecipientDTO>("TeamMapper.getTeamForDifferentialAllocation",data)
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



    fun getTeamForSpecialAllocation(ccmId: String): List<Team>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",ccmId)

        return sqlSessionFactory.openSession().selectList<Team>("TeamMapper.getTeamForSpecialAllocation",data)



    }


    fun getRecipientForSpecialAllocation(teamId: String): List<Recipient>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",teamId)

        return sqlSessionFactory.openSession().selectList<Recipient>("RecipientMapper.getRecipientForSpecialAllocation",data)


    }


    fun saveSpecialAllocation(saveAlloc: List<saveDifferentialAllocation>) {
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


    fun deleteSpecialAllocation(dipId: String): Map<String, Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var plan = DispatchPlan()

        lateinit var jsonResult : Map<String, Any>

        try{
            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("id",dipId)

            plan = sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanById",data)

            if(plan == null) {
                jsonResult = mapOf("success" to false, "message" to "Special dispatch not found")

                return jsonResult

            }else {

                var data0: MutableMap<String, Any> = mutableMapOf()

                data0.put("ID_DIP",dipId)

                sqlSessionFactory.openSession().update("DispatchPlanMapper.resetDraftPlan",data0)

                var data1: MutableMap<String, Any> = mutableMapOf()

                data1.put("id",dipId)

                sqlSessionFactory.openSession().delete("DispatchPlanMapper.deleteSpecialAllocation",data1)

                jsonResult = mapOf("success" to true, "message" to "Special dispatch deleted successfully.")

                return jsonResult

            }
        } catch (e:Exception){
            mapOf("success" to false, "message" to "Error occurred while deleting special dispatch")
        }

        return jsonResult



    }




    fun deleteSpecialAllocationDID(dipId: String): Map<String, Any>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        lateinit var jsonResult : Map<String, Any>

        try{
            var dispatchDetail = mutableListOf<DispatchDetail>()

            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("id",dipId)

            dispatchDetail = sqlSessionFactory.openSession().selectList<DispatchDetail>("DispatchDetailMapper.deleteSpecialAllocationDID",data)

            if(dispatchDetail == null){
                jsonResult = mapOf("success" to false, "message" to "Allocation not found")

                return jsonResult
            } else{

                dispatchDetail.forEach {it ->
                    var data0: MutableMap<String, Any> = mutableMapOf()

                    var inv = Inventory()

                    data0.put("id",it.inventoryId!!.id)

                     inv = sqlSessionFactory.openSession().selectOne("InventoryMapper.getInventoryForSpecialAllocation",data0)

                    var invAllocatedQty = inv.qtyAllocated!!.minus(it.qtyDispatch!!)

                    var data1: MutableMap<String, Any> = mutableMapOf()

                    data1.put("id",inv.id)
                    data1.put("qtyAllocated", invAllocatedQty)
                    data1.put("updatedBy",user.id)

                    sqlSessionFactory.openSession().update("InventoryMapper.deleteSpecialAllocationDID",data1)


                    var data2: MutableMap<String, Any> = mutableMapOf()

                    data2.put("id",it.id)

                    sqlSessionFactory.openSession().delete("DispatchDetailMapper.specialAllocation",data2)




                }


                jsonResult = mapOf("success" to true, "message" to "Special dispatch deleted successfully.")

                return jsonResult
            }


        }catch (e:Exception){
            mapOf("success" to false, "message" to "Error occurred while deleting allocation")
            return jsonResult
        }

        return jsonResult


    }



    fun createVirtualPlan(yearMonth: Long): List<AllocationInventoryDetailsWithCostCenterDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        val month = (yearMonth % 100).toInt()
        val year = (yearMonth / 100).toInt()


        var tseID = getTseList(user.id)

        var allocationInventoryDetails = mutableListOf<AllocationInventoryDetailsWithCostCenterDTO>()

        var plan = DispatchPlan()

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("month",month)
        data.put("year",year)
        data.put("owner",user.id)

        plan = sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.createVirtualPlan",data)

        var isRbmOrNsm = false;

        if(user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id || user.userDesignation!!.id == UserRoleEnum.NATIONAL_SALES_MANAGER_ID.id){
            isRbmOrNsm = true;
        }

            var ulv = UserDesignation()

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("id",user.userDesignation!!.id)

        ulv = sqlSessionFactory.openSession().selectOne("UserDesignationMapper.createVirtualPlan",data0)

        if(plan == null) {

            var data1: MutableMap<String, Any> = mutableMapOf()

            var planId = UUID.randomUUID().toString()

            data1.put("id",planId)
            data1.put("owner",user.id)
            data1.put("month",month)
            data1.put("year",year)
            data1.put("planStatus",AllocationStatusEnum.DRAFT.id)
            data1.put("isSpecial", 1)
            data1.put("remarks", "virtualplan")
            data1.put("createdBy",user.id)
            data1.put("updatedBy",user.id)
            data1.put("invoiceStatus",DispatchPlanInvoiceStatus.NOT_INITIATED.id)
            data1.put("isVirtual",1)

            sqlSessionFactory.openSession().insert("DispatchPlanMapper.insertVirtualPlan",data1)

        }
        var bmPlanId = mutableListOf<VirtualDispatchDetail>()

        if(isRbmOrNsm){
            var data2: MutableMap<String, Any> = mutableMapOf()

            data2.put("month",month)
            data2.put("year",year)
            data2.put("owner",user.userRecipientId!!)

            bmPlanId == sqlSessionFactory.openSession().selectList<VirtualDispatchDetail>("VirtualDispatchDetailMapper.createVirtualPlan",data2)

            bmPlanId.forEach {  it ->
                var data3: MutableMap<String, Any> = mutableMapOf()

                data3.put("id",plan.id)
                data3.put("invId", it.inventoryId!!)

                var rbmdetails = sqlSessionFactory.openSession().selectList<DispatchDetail>("DispatchDetailMapper.createVirtualPlan",data3)

                var status = SystemLov()

                var data4: MutableMap<String, Any> = mutableMapOf()

                data4.put("id",plan.id)

                var bmPlan = sqlSessionFactory.openSession().selectOne<DispatchPlan>("DispatchPlanMapper.getDispatchPlanById",data4)

                if(bmPlan.isVirtual == 1 && bmPlan.planStatus!!.id == AllocationStatusEnum.SUBMIT.id){
                    if(rbmdetails == null) {

                        var detail = DispatchDetail()

                        var data5: MutableMap<String, Any> = mutableMapOf()

                        var didId = UUID.randomUUID().toString()

                        data5.put("id",didId)
                        data5.put("planId",plan.id)
                        data5.put("inventoryId",it.inventoryId!!)
                        data5.put("recipientId",it.recipientId!!)
                        data5.put("qtyDispatch",it.qtyDispatch!!)
                        data5.put("quarterlyPlanId","00000000-0000-0000-0000-000000000000")
                        data5.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
                        data5.put("createdBy",user.id)
                        data5.put("updatedBy",user.id)

                        sqlSessionFactory.openSession().insert("DispatchDetailMapper.insertVirtualPlan",data5)

                        var data6: MutableMap<String, Any> = mutableMapOf()

                        data6.put("id", it.id)
                        data6.put("qtyDispatch", 0)

                        sqlSessionFactory.openSession().update("VirtualDispatchDetailMapper.updateVirtualPlan",data6)









                    }
                }


            }


        }


        if(isRbmOrNsm){

            var data7: MutableMap<String, Any> = mutableMapOf()

            data7.put("UserID", user.userRecipientId!!)
            data7.put("PlanID", plan.id)

            allocationInventoryDetails = sqlSessionFactory.openSession().selectList<AllocationInventoryDetailsWithCostCenterDTO>("ReportMapper.GetVirtualAllocationDetailsforRbm",data7)


        } else {
            var data8: MutableMap<String, Any> = mutableMapOf()

            data8.put("UserID", user.userRecipientId!!)
            data8.put("PlanID", plan.id)

            allocationInventoryDetails = sqlSessionFactory.openSession().selectList<AllocationInventoryDetailsWithCostCenterDTO>("ReportMapper.GetVirtualAllocationInventoryWithCostCenter",data8)


        }

        if(plan.planStatus!!.id == AllocationStatusEnum.SUBMIT.id || plan.planStatus!!.id == AllocationStatusEnum.APPROVED.id) {

            allocationInventoryDetails = allocationInventoryDetails.filter { it.qtyAllocated != null && it.qtyAllocated!! > 0  }.sortedByDescending { it.qtyAllocated }.toMutableList()
        }

        return  allocationInventoryDetails


    }




    fun isVirtualPlanApprovedOrSubmitLock(month: String, year: String): Map<String, Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var allocationStatus = ""

        var allocationInvoiceStatus = ""

       lateinit var jsonResult : Map<String, Any>

        try{
            var allocationTypes = mutableListOf<SystemLov>()

            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("type",SLVTypeEnum.MONTHLY_PLAN_STATUS.type)

            allocationTypes = sqlSessionFactory.openSession().selectList<SystemLov>("SystemLovMapper.isVirtualPlanApprovedOrSubmitLock",data)

            var plan = DispatchPlan()

            var data0: MutableMap<String, Any> = mutableMapOf()

            data0.put("month",month)
            data0.put("year",year)
            data0.put("owner",user.id)

            plan = sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.createVirtualPlan",data0)

            var data1: MutableMap<String, Any> = mutableMapOf()

            data1.put("id",plan.planStatus!!.id)

            allocationStatus = sqlSessionFactory.openSession().selectOne("SystemLovMapper.allocationStatus",data1)

            var api = ApprovalChainTransaction()

            var data2: MutableMap<String, Any> = mutableMapOf()

            data2.put("id",plan.id)

            api = sqlSessionFactory.openSession().selectOne("ApprovalChainTransactionMapper.getApprovalChainById",data2)

            if(api != null){
                allocationStatus = "$allocationStatus(Last Comments-: ${api.comments})"
            }

            var data3: MutableMap<String, Any> = mutableMapOf()

            data3.put("id",plan.planStatus!!.id)
            data3.put("type",SLVTypeEnum.DISPATCH_PLAN_INVOICE_STATUS.type)

            allocationInvoiceStatus = sqlSessionFactory.openSession().selectOne("SystemLovMapper.isVirtualPlanApprovedOrSubmitLockAllocationInvoiceStatus",data3)

            var monthToSubtract = 0

            var data4: MutableMap<String, Any> = mutableMapOf()

            data4.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK_OF_MONTH.id)

            monthToSubtract = sqlSessionFactory.openSession().selectOne("SystemPropertiesMapper.monthToSubtract",data4)

            var monthInt = month.toInt()
            var yearInt = year.toInt()

            val now = LocalDateTime.now()
            val firstDayOfMonth = LocalDateTime.of(yearInt, monthInt, 1, 0, 0)

            var dayToAdd = 0 ;
            var dayToAddBm = 0 ;
            var dayToAddBex = 0 ;


            if(plan != null){
                if(plan.planStatus!!.id == AllocationStatusEnum.APPROVED.id ){
                     jsonResult = mapOf(
                        "IsPlanSubmit" to true,
                        "Message" to "Plan Approved",
                        "AllocationStatus" to allocationStatus,
                        "AllocationInvoiceStatus" to allocationInvoiceStatus
                    )

                    return jsonResult
                }


                if(plan.planStatus!!.id == AllocationStatusEnum.SUBMIT.id ){
                    jsonResult = mapOf(
                        "IsPlanSubmit" to true,
                        "Message" to "Plan Submit",
                        "AllocationStatus" to allocationStatus,
                        "AllocationInvoiceStatus" to allocationInvoiceStatus
                    )

                    return jsonResult
                }

                if(plan.planStatus!!.id == AllocationStatusEnum.DRAFT.id){
                    var data5: MutableMap<String, Any> = mutableMapOf()

                    data5.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK.id)

                    dayToAdd = sqlSessionFactory.openSession().selectOne("SystemPropertiesMapper.isVirtualPlanApprovedOrSubmitLockDayToAdd",data5)
                }


                if(plan.planStatus!!.id == AllocationStatusEnum.UNLOCK.id){
                    var data6: MutableMap<String, Any> = mutableMapOf()

                    data6.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK.id)

                    dayToAddBm = sqlSessionFactory.openSession().selectOne("SystemPropertiesMapper.isVirtualPlanApprovedOrSubmitLockDayToAdd",data6)

                    var data7: MutableMap<String, Any> = mutableMapOf()

                    data7.put("id",SystemPropertyEnum.BEX_MONTH_PLAN_UNLOCK.id)

                    dayToAddBex = sqlSessionFactory.openSession().selectOne("SystemPropertiesMapper.isVirtualPlanApprovedOrSubmitLockDayToAdd",data7)

                    dayToAdd = dayToAddBm + dayToAddBex




                }





            } else {

                var data8: MutableMap<String, Any> = mutableMapOf()

                data8.put("id",SystemPropertyEnum.BM_MONTH_PLAN_LOCK.id)

                dayToAdd = sqlSessionFactory.openSession().selectOne("SystemPropertiesMapper.isVirtualPlanApprovedOrSubmitLockDayToAdd",data8)

            }

            var monthToSubtractLong = monthToSubtract.toLong()
            var dayToAddLong = dayToAdd.toLong()

            val calculatedDate = firstDayOfMonth.minusMonths(monthToSubtractLong).plusDays(dayToAddLong - 1)
            val isPlanLock = calculatedDate.isBefore(now)

            if(isPlanLock){
                jsonResult = mapOf(
                    "IsPlanSubmit" to true,
                    "Message" to "Plan Lock",
                    "AllocationStatus" to allocationStatus,
                    "AllocationInvoiceStatus" to allocationInvoiceStatus
                )

                return jsonResult
            }

            val tempFutureCheckActual = LocalDateTime.of(yearInt, monthInt, 1, 0, 0)
            val tempFutureCheckNow = LocalDateTime.of(now.year, now.month, 1, 0, 0)

            var futureMonthCheck = 0 ;
            var allocationFutureMonthToCheck = 2;

            futureMonthCheck = allocationFutureMonthToCheck

            val monthsBetween = ChronoUnit.MONTHS.between(
                tempFutureCheckActual, tempFutureCheckNow
            ).toInt()

            if(monthsBetween >= futureMonthCheck){
                jsonResult = mapOf(
                    "IsPlanSubmit" to true,
                    "Message" to "Future Plan Lock",
                    "AllocationStatus" to "",
                    "AllocationInvoiceStatus" to ""
                )
                return jsonResult
            }

            jsonResult = mapOf(
                "IsPlanSubmit" to false,
                "Message" to "",
                "AllocationStatus" to allocationStatus,
                "AllocationInvoiceStatus" to allocationInvoiceStatus
            )
            return jsonResult



        }catch (e :Exception){
            jsonResult = mapOf(
                "IsPlanSubmit" to false,
                "Message" to "",
                "AllocationStatus" to allocationStatus,
                "AllocationInvoiceStatus" to allocationInvoiceStatus
            )
            return jsonResult
        }


    }


    fun getVirtualTeamForCommonAllocation(ccmId: String): List<CommonAllocationTeamDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var recipient = Recipient()

        var data1: MutableMap<String, Any> = mutableMapOf()
        data1.put("id",user.id)

        recipient = sqlSessionFactory.openSession().selectOne("RecipientMapper.getRecipientForVirtualTeamForCommonAllocation",data1)



        var virtualCommonTeam = mutableListOf<CommonAllocationTeamDTO>()

        if(user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id){

            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("code",recipient.rmCode!!)

            virtualCommonTeam = sqlSessionFactory.openSession().selectList<CommonAllocationTeamDTO>("TeamMapper.getVirtualTeamForCommonAllocation",data)


        } else {
            var data0: MutableMap<String, Any> = mutableMapOf()

            data0.put("ccmId",ccmId)

            virtualCommonTeam =  sqlSessionFactory.openSession().selectList<CommonAllocationTeamDTO>("TeamMapper.getTeamForCommonAllocation",data0)
        }

        return virtualCommonTeam
    }


    fun getVirtualQuantityAllocatedToUser(userId: String,inventoryId: String, month: Int, year: Int, isSpecialDispatch: Int,planId: String): List<DesignationWiseQuantityAllocatedDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var quantityDispatch = mutableListOf<DesignationWiseQuantityAllocatedDTO>()

        if(user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id){
            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("month",month)
            data.put("year",year)
            data.put("inventoryId",inventoryId)
            data.put("isSpecialDispatch",isSpecialDispatch)
            data.put("planId",planId)

            quantityDispatch =  sqlSessionFactory.openSession().selectList<DesignationWiseQuantityAllocatedDTO>("DispatchDetailMapper.getVirtualQuantityAllocatedToUserForRbm",data)
        } else {
            quantityDispatch = getQuantityAllocatedOfUserToItem(userId,inventoryId,month,year,isSpecialDispatch).toMutableList()


        }

        return quantityDispatch

    }



    fun getVirtualTeamForDifferentialAllocation(planId: String, teamId: String, inventoryId: String): List<AllocationDataTeamPopupDetailsDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var teamPopupDetails = mutableListOf<AllocationDataTeamPopupDetailsDTO>()

        if(user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id ) {
            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("PlanID",planId)
            data.put("TeamID",teamId)
            data.put("InventoryID",inventoryId)

            teamPopupDetails = sqlSessionFactory.openSession().selectList<AllocationDataTeamPopupDetailsDTO>("TeamMapper.getVirtualTeamForDifferentialAllocation",data)


        } else {
            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("PlanID",planId)
            data.put("TeamID",teamId)
            data.put("InventoryID",inventoryId)

            teamPopupDetails = sqlSessionFactory.openSession().selectList<AllocationDataTeamPopupDetailsDTO>("TeamMapper.getVirtualTeamForDifferentialAllocation",data)
        }

        return teamPopupDetails

    }



    fun saveVirtualCommonAllocation(saveAlloc: List<saveVirtualCommonAllocationDTO>) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var recipient = mutableListOf<Recipient>()

        if(user.userDesignation!!.id != UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id ){
            saveAlloc.forEach {it ->
                var data : MutableMap<String,Any> = mutableMapOf()
                var virtualDispatchDetail = VirtualDispatchDetail()
                var vidId = UUID.randomUUID().toString()
                data.put("id",vidId)
                data.put("planId",it.dispatchPlanId!!)
                data.put("inventoryId",it.inventoryId!!)
                data.put("recipientId",it.recipientId!!)
                data.put("qtyDispatch",it.quantity!!)
                data.put("quarterlyPlanId","00000000-0000-0000-0000-000000000000")
                data.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
                data.put("createdBy",user.id)
                data.put("updatedBy",user.id)

                sqlSessionFactory.openSession().insert("VirtualDispatchDetailMapper.insertVid",data)



            }
        } else {
            saveAlloc.forEach {
                var data0 : MutableMap<String,Any> = mutableMapOf()
                var virtualDispatchDetail = VirtualDispatchDetail()
                var vidId = UUID.randomUUID().toString()
                data0.put("id",vidId)
                data0.put("planId",it.dispatchPlanId!!)
                data0.put("inventoryId",it.inventoryId!!)
                data0.put("recipientId",it.recipientId!!)
                data0.put("qtyDispatch",it.quantity!!)
                data0.put("quarterlyPlanId","00000000-0000-0000-0000-000000000000")
                data0.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
                data0.put("createdBy",user.id)
                data0.put("updatedBy",user.id)

                sqlSessionFactory.openSession().insert("VirtualDispatchDetailMapper.insertVid",data0)


                var data1 : MutableMap<String,Any> = mutableMapOf()
                data1.put("id",it.dispatchPlanId!!)
                data1.put("qtyDispatch",0)
                data1.put("updatedBy",user.id)

                sqlSessionFactory.openSession().update("DispatchDetailMapper.saveVirtualCommonAllocation",data1)

            }


        }

    }



    fun submitVirtualAllocation(alloc: submitAllocationDTO) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var isUserRbmOrNsm = false;

        if(user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id || user.userDesignation!!.id == UserRoleEnum.NATIONAL_SALES_MANAGER_ID.id  ){
            isUserRbmOrNsm = true;
        }

        var plan = DispatchPlan()

        var data: MutableMap<String , Any> = mutableMapOf()

        data.put("month",alloc.month!!)
        data.put("year",alloc.year!!)
        data.put("owner",user.id)

        plan = sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.createVirtualPlan",data)

        var data0: MutableMap<String , Any> = mutableMapOf()

        data0.put("id",plan.id)
        data0.put("planStatus",AllocationStatusEnum.SUBMIT.id)
        data0.put("invoiceStatus",DispatchPlanInvoiceStatus.NOT_INITIATED.id)
        data0.put("updatedBy",user.id)

        sqlSessionFactory.openSession().update("DispatchPlanMapper.submitVirtualAllocation",data0)

        var virtualDispatch = mutableListOf<VirtualDispatchDetail>()

        var data1: MutableMap<String , Any> = mutableMapOf()

        data1.put("id",plan.id)

        virtualDispatch = sqlSessionFactory.openSession().selectList<VirtualDispatchDetail>("VirtualDispatchDetailMapper.submitVirtualAllocation",data1)

        if(virtualDispatch.count() > 0 ){
            virtualDispatch.forEach {it ->
                var virtualDid = VirtualDispatchDetail()

                var data2: MutableMap<String , Any> = mutableMapOf()

                var vidId = UUID.randomUUID().toString()

                data2.put("id",vidId)
                data2.put("planId",it.planId!!)
                data2.put("inventoryId",it.inventoryId!!)
                data2.put("recipientId",it.recipientId!!)
                data2.put("qtyDispatch", it.qtyDispatch!!)
                data2.put("quarterlyPlanId","00000000-0000-0000-0000-000000000000")
                data2.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
                data2.put("createdBy",user.id)
                data2.put("updatedBy",user.id)

                sqlSessionFactory.openSession().insert("VirtualDispatchDetailMapper.insertVirtualDispatch",data2)

                var data3: MutableMap<String , Any> = mutableMapOf()
                data3.put("id",it.inventoryId!!)

                var inv = sqlSessionFactory.openSession().selectOne<Inventory>("InventoryMapper.submitVirtualAllocation",data3)

                if(!isUserRbmOrNsm){
                    var invQtyAllocated = inv.qtyAllocated!! - it.qtyDispatch!!

                    if(invQtyAllocated > 0 ){
                        var data4: MutableMap<String , Any> = mutableMapOf()

                        data4.put("id",inv.id)
                        data4.put("qtyAllocated",invQtyAllocated)
                        data4.put("updatedBy",user.id)

                        sqlSessionFactory.openSession().update("InventoryMapper.submitVirtualAllocationInventory",data4)
                    } else {
                        var data5: MutableMap<String , Any> = mutableMapOf()

                        data5.put("id",inv.id)
                        data5.put("qtyAllocated",invQtyAllocated)
                        data5.put("updatedBy",user.id)

                        sqlSessionFactory.openSession().update("InventoryMapper.submitVirtualAllocationInventory",data5)
                    }
                }


            }
        }


        var brandsList =  mutableListOf<BrandManager>()

        var data6: MutableMap<String , Any> = mutableMapOf()

        data6.put("id",user.id)

        brandsList = sqlSessionFactory.openSession().selectList<BrandManager>("BrandManagerMapper.submitVirtualAllocation",data6)

        brandsList.forEach {it ->
            var dpbt = DispatchPlanBrand()
            var dpbtId = UUID.randomUUID().toString()

            var data7: MutableMap<String , Any> = mutableMapOf()

            data7.put("id",dpbtId)
            data7.put("dipId",plan.id)
            data7.put("brdId",it.brandId!!.id)
            data7.put("dipOwnerId",plan.owner!!.id)
            data7.put("createdBy",user.id)
            data7.put("updatedBy",user.id)

            sqlSessionFactory.openSession().insert("DispatchPlanBrandMapper.submitVirtualAllocation",data7)


        }



        var approvalChainTransaction = ApprovalChainTransaction()
        var data8: MutableMap<String, Any> = mutableMapOf()
        data8.put("id",plan.id)

        approvalChainTransaction = sqlSessionFactory.openSession().selectOne("ApprovalChainTransactionMapper.getApprovalChainById",data8)

        if(approvalChainTransaction != null){
            var data9: MutableMap<String, Any> = mutableMapOf()

            data9.put("owner",plan.id)
            data9.put("apiStatus",ApprovalStatusEnum.PENDING_APPROVAL.id)
            data9.put("updatedBy",user.id)

            sqlSessionFactory.openSession().update("ApprovalChainTransactionMapper.updateSaveMonthlyToSpecial",data9)

        }else {
            var data10: MutableMap<String, Any> = mutableMapOf()

            data10.put("id",UUID.randomUUID().toString())
            data10.put("owner",plan.id)
            data10.put("designation",UserRoleEnum.BEX_ID.id)
            data10.put("apiStatus",ApprovalStatusEnum.PENDING_APPROVAL.id)
            data10.put("createdBy",user.id)
            data10.put("updatedBy",user.id)

            sqlSessionFactory.openSession().update("ApprovalChainTransactionMapper.insertSaveMonthlyToSpecial",data10)
        }





    }


    fun getActiveUsers(userId: String) : List<ActiveUsersDTO>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("UserID",userId)

       return  sqlSessionFactory.openSession().selectList<ActiveUsersDTO>("AllocationRuleMapper.getActiveUsers",data)


    }



    fun getDownloadAllocation(planId: String): List<DownloadAllocationDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("planid",planId)

        return sqlSessionFactory.openSession().selectList<DownloadAllocationDTO>("AllocationRuleMapper.getDownloadAllocation",data)

    }


    fun getBlockedRecipients(code: String): List<BlockedRecipientDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("code",code)

        return sqlSessionFactory.openSession().selectList<BlockedRecipientDTO>("AllocationRuleMapper.getBlockedRecipients",data)

    }




}


















