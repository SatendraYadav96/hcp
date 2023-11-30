package com.squer.promobee.service.repository


import com.squer.promobee.api.v1.enums.*
import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.ApprovalChainTransaction
import com.squer.promobee.service.repository.domain.DispatchDetail
import com.squer.promobee.service.repository.domain.DispatchPlan
import com.squer.promobee.service.repository.domain.Users
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ApprovalRepository(
    securityUtility: SecurityUtility
): BaseRepository<Users>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    @Autowired
    lateinit var masterRepository: MasterRepository


    @Autowired
    lateinit var invoiceRepository: InvoiceRepository


    // MONTHLY APPROVAL


    fun getMonthlyApprovalForBex(month: Int, year: Int, userId: String, userDesgId: String): List<MontlyApprovalBexDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("Month", month)
        data.put("Year", year)
        data.put("UserID", userId)
        data.put("UserDesignation", userDesgId)
        return sqlSessionFactory.openSession().selectList("ApprovalMapper.getMonthlyApprovalForBex", data)
    }

    fun getDispatchPlanById(id: String): DispatchPlan {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("id", id)

        return sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanById",data)
    }


    fun getMonthlyApprovalDetails(userId: String, planId: String): List<AllocationInventoryDetailsWithCostCenterDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        var plan = getDispatchPlanById(planId)

        var usr = masterRepository.getUserById(userId)

        var isuserRbm = (usr.userDesignation!!.id == UserLovEnum.REGIONAL_BUSINESS_MANAGER.id || usr.userDesignation!!.id == UserLovEnum.NATIONAL_SALES_MANAGER.id)



        //var isuserRbm = (plan.owner!!.id == UserLovEnum.REGIONAL_BUSINESS_MANAGER.id  || plan.owner!!.id == UserLovEnum.NATIONAL_SALES_MANAGER.id)

        var allocationDetails = mutableListOf<AllocationInventoryDetailsWithCostCenterDTO>()

        var filteredAllocationDetails = mutableListOf<AllocationInventoryDetailsWithCostCenterDTO>()

        if(isuserRbm){

            data.put("USERID", userId)
            data.put("RBMPLANID", planId)


            allocationDetails =  sqlSessionFactory.openSession().selectList("ApprovalMapper.allocationDetailsRbm",data)



        }else{

            data.put("UserID", userId)
            data.put("PlanID", planId)

            allocationDetails =  sqlSessionFactory.openSession().selectList("ApprovalMapper.allocationDetailsBrandManager", data)
        }

        // Filter allocation details based on quantityAllocated
         filteredAllocationDetails = allocationDetails.filter { (it.quantityAllocated ?: 0) > 0 }.toMutableList()


        return filteredAllocationDetails



    }


    fun unlockPlanForUserByMonthAndYear(plan: UnlockPlanDto) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        var dipId = plan.dispatchPlanId

        var dip = DispatchPlan()

        var planId = UUID.randomUUID().toString()

        if(dipId!!.isEmpty()){

            data.put("id", planId)
            plan.userId?.let { data.put("owner", it) }
            plan.month?.let { data.put("month", it) }
            plan.year?.let { data.put("year", it) }
           data.put("planStatus", AllocationEnum.UNLOCK.id)
            data.put("isSpecial", 0)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("invoiceStatus",DispatchPlanInvoiceStatus.NOT_INITIATED.id)

            sqlSessionFactory.openSession().insert("DispatchPlanMapper.insertUnlockPlan",data)

        }else{
            //var existingDip = plan.dispatchPlanId?.let { invoiceRepository.getDispatchPlanById(it) }


            data.put("id", dipId)
            data.put("planStatus", AllocationEnum.UNLOCK.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)

            sqlSessionFactory.openSession().update("DispatchPlanMapper.updateUnlockPlan",data)


        }


    }



    fun resetDraftPlan(planId: String) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("ID_DIP",planId)

        sqlSessionFactory.openSession().update("DispatchPlanMapper.resetDraftPlan",data)


    }


    fun getApprovalChainById(id: String): ApprovalChainTransaction {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)

       return sqlSessionFactory.openSession().selectOne<ApprovalChainTransaction>("ApprovalChainTransactionMapper.getApprovalChainById",data)


    }

    fun getApprovalChainForSpecialPlanConvert(id: String): ApprovalChainTransaction {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)


        return sqlSessionFactory.openSession().selectOne<ApprovalChainTransaction>("ApprovalChainTransactionMapper.getApprovalChainForSpecialPlanConvert",data)


    }


    fun approvePlan(plan: ApproveRejectPlanDto) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        var isSpecial : Int =  plan.approvalType!!.toInt()

        var approvalTransaction = plan.apiId?.let { getApprovalChainById(it) }

        var dispatchPlan = plan.planId?.let { getDispatchPlanById(it) }

        plan.apiId?.let { data.put("id", it) }
        data.put("approvedByUser",user.id)
        data.put("apiStatus",ApprovalStatusEnum.APPROVED.id)
        plan.comment?.let { data.put("comments", it) }
        data.put("updatedBy",user.id)


        sqlSessionFactory.openSession().update("ApprovalChainTransactionMapper.updateApprovalChainTransaction",data)

        if(user.userDesignation!!.id == UserLovEnum.BUH.id && isSpecial == 1 ){

            var data: MutableMap<String, Any> = mutableMapOf()



            plan.planId?.let { data.put("id", it) }
            data.put("planStatus", AllocationStatusEnum.APPROVED_BY_BUH.id)
            data.put("updatedBy",user.id)

            sqlSessionFactory.openSession().update("DispatchPlanMapper.approvePlan",data)

        }
        if (user.userDesignation!!.id == UserLovEnum.BEX.id && isSpecial == 1){
            var data: MutableMap<String, Any> = mutableMapOf()

            plan.planId?.let { data.put("id", it) }
            data.put("planStatus", AllocationStatusEnum.APPROVED.id)
            data.put("updatedBy",user.id)

            sqlSessionFactory.openSession().update("DispatchPlanMapper.approvePlan",data)
        }
        if(user.userDesignation!!.id == UserLovEnum.BEX.id && isSpecial == 0){
            var data: MutableMap<String, Any> = mutableMapOf()

            plan.planId?.let { data.put("id", it) }
            data.put("planStatus", AllocationStatusEnum.APPROVED.id)
            data.put("updatedBy",user.id)

            sqlSessionFactory.openSession().update("DispatchPlanMapper.approvePlan",data)

        }

    }

    fun getDispatchDetails(planId: String): List<DispatchDetail> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("planId",planId)

        return  sqlSessionFactory.openSession().selectList("DispatchDetailMapper.getDispatchDetailsRejectPlan",data)


    }


    fun getDispatchPlanCount(id: String): DispatchPlan {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)

        return  sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanCount",data)


    }



    fun rejectPlan(plan: ApproveRejectPlanDto) {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        var isSpecial: Int = plan.approvalType!!.toInt()

       // var approvalTransaction = plan.apiId?.let { getApprovalChainById(it) }

        var dispatchPlan = plan.planId?.let { getDispatchPlanById(it) }

       var dispatchDetails = plan.planId?.let { getDispatchDetails(it) }

        if(dispatchPlan!!.planStatus!!.id == AllocationStatusEnum.SUBMIT.id ){
            plan.apiId?.let { data.put("id", it) }
            data.put("approvedByUser", user.id)
            data.put("apiStatus", ApprovalStatusEnum.REJECTED.id)
            plan.comment?.let { data.put("comments", it) }
            data.put("updatedBy", user.id)


            sqlSessionFactory.openSession().update("ApprovalChainTransactionMapper.updateApprovalChainTransaction", data)
        }




        if(user.userDesignation!!.id == UserLovEnum.BUH.id && isSpecial == 1 ){

            var data: MutableMap<String, Any> = mutableMapOf()

            plan.planId?.let { data.put("id", it) }
            data.put("planStatus", AllocationStatusEnum.DRAFT.id)
            data.put("updatedBy",user.id)
            data.put("invoiceStatus",DispatchPlanInvoiceStatus.NOT_INITIATED.id)

            sqlSessionFactory.openSession().update("DispatchPlanMapper.rejectPlan",data)


            var i = 0
            dispatchDetails!!.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                data.put("id", dispatchDetails.get(i).id)
                data.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
                data.put("updatedBy",user.id)

                sqlSessionFactory.openSession().update("DispatchDetailMapper.rejectPlanDispatchDetails",data)

                i++

            }


        }

        if(user.userDesignation!!.id == UserLovEnum.BEX.id && isSpecial == 0){
            var data: MutableMap<String, Any> = mutableMapOf()

            plan.planId?.let { data.put("id", it) }
            data.put("planStatus", AllocationStatusEnum.DRAFT.id)
            data.put("updatedBy",user.id)
            data.put("invoiceStatus",DispatchPlanInvoiceStatus.NOT_INITIATED.id)


            sqlSessionFactory.openSession().update("DispatchPlanMapper.rejectPlan",data)

            var i = 0

            dispatchDetails!!.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                data.put("id", dispatchDetails.get(i).id)
                data.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
                data.put("updatedBy",user.id)

                sqlSessionFactory.openSession().update("DispatchDetailMapper.rejectPlanDispatchDetails",data)

                i++
            }



        }

        if(user.userDesignation!!.id == UserLovEnum.BEX.id && isSpecial == 1){
            var data: MutableMap<String, Any> = mutableMapOf()

            plan.planId?.let { data.put("id", it) }
            data.put("planStatus", AllocationStatusEnum.DRAFT.id)
            data.put("updatedBy",user.id)
            data.put("invoiceStatus",DispatchPlanInvoiceStatus.NOT_INITIATED.id)


            sqlSessionFactory.openSession().update("DispatchPlanMapper.rejectPlan",data)

            var i = 0

            dispatchDetails!!.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                data.put("id", dispatchDetails.get(i).id)
                data.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
                data.put("updatedBy",user.id)

                sqlSessionFactory.openSession().update("DispatchDetailMapper.rejectPlanDispatchDetails",data)

                i++
            }



        }


        if(user.userDesignation!!.id == UserLovEnum.BEX.id && isSpecial == 2){
            var data: MutableMap<String, Any> = mutableMapOf()

            plan.planId?.let { data.put("id", it) }
            data.put("planStatus", AllocationStatusEnum.DRAFT.id)
            data.put("updatedBy",user.id)
            data.put("invoiceStatus",DispatchPlanInvoiceStatus.NOT_INITIATED.id)


            sqlSessionFactory.openSession().update("DispatchPlanMapper.rejectPlan",data)

            var i = 0

            dispatchDetails!!.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                data.put("id", dispatchDetails.get(i).id)
                data.put("detailStatus",DispatchDetailStatusEnum.ALLOCATED.id)
                data.put("updatedBy",user.id)

                sqlSessionFactory.openSession().update("DispatchDetailMapper.rejectPlanDispatchDetails",data)

                i++
            }



        }


    }




    fun saveMonthlyToSpecial(plan: SaveMonthlyToSpecialDTO) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        var isSaved = false
        var ErrorMsg = ""

        if(plan.isSpecialChange == true) {
            var dip = plan.planId?.let { getDispatchPlanById(it) }
            if(dip!!.planStatus!!.id == AllocationStatusEnum.DRAFT.id ) {

                plan.planId?.let { data.put("id", it) }
                plan.month?.let { data.put("month", it) }
                plan.year?.let { data.put("year", it) }
                data.put("isSpecial",1)
                plan.planPurpose?.let { data.put("remarks", it) }
                data.put("planStatus",AllocationStatusEnum.DRAFT.id)
                data.put("updatedBy", user.id)

                sqlSessionFactory.openSession().update("DispatchPlanMapper.saveMonthlyToSpecial",data)

                isSaved = true;
                ErrorMsg = "Plan changed from Monthly to Special Successfully...";

            } else{

                plan.planId?.let { data.put("id", it) }
                plan.month?.let { data.put("month", it) }
                plan.year?.let { data.put("year", it) }
                data.put("isSpecial",1)
                plan.planPurpose?.let { data.put("remarks", it) }
                data.put("planStatus",AllocationStatusEnum.SUBMIT.id)
                data.put("updatedBy", user.id)

                sqlSessionFactory.openSession().update("DispatchPlanMapper.saveMonthlyToSpecial",data)


//                var approvalChainTransaction  = ApprovalChainTransaction()

                var approvalChainTransaction = plan.planId?.let { getApprovalChainForSpecialPlanConvert(it) }!!

                if(approvalChainTransaction != null && approvalChainTransaction.designation!!.id == UserLovEnum.BEX.id) {

                    var data: MutableMap<String, Any> = mutableMapOf()
                    data.put("owner", plan.planId!!)
                    data.put("apiStatus", ApprovalStatusEnum.PENDING_APPROVAL.id)
                    data.put("updatedBy",user.id)

                    sqlSessionFactory.openSession().update("ApprovalChainTransactionMapper.updateSaveMonthlyToSpecial",data)

                } else {
                    var apiId = UUID.randomUUID().toString()

                    data.put("id",apiId)
                    data.put("owner", plan.planId!!)
                    data.put("designation", UserLovEnum.BEX.id)
                    data.put("apiStatus", ApprovalStatusEnum.PENDING_APPROVAL.id)
                    data.put("createdBy",user.id)
                    data.put("updatedBy",user.id)

                    sqlSessionFactory.openSession().insert("ApprovalChainTransactionMapper.insertSaveMonthlyToSpecial",data)
                }

                var approvalChainTransactionBUH = plan.planId?.let { getApprovalChainForSpecialPlanConvert(it) }!!

                if(approvalChainTransactionBUH != null && approvalChainTransactionBUH.designation!!.id == UserLovEnum.BUH.id) {

                    var data: MutableMap<String, Any> = mutableMapOf()
                    data.put("owner", plan.planId!!)
                    data.put("apiStatus", ApprovalStatusEnum.PENDING_APPROVAL.id)
                    data.put("updatedBy",user.id)

                    sqlSessionFactory.openSession().update("ApprovalChainTransactionMapper.updateSaveMonthlyToSpecial",data)

                } else {

                    var apiId1 = UUID.randomUUID().toString()

                    data.put("id",apiId1)
                    data.put("owner", plan.planId!!)
                    data.put("designation", UserLovEnum.BEX.id)
                    data.put("apiStatus", ApprovalStatusEnum.PENDING_APPROVAL.id)
                    data.put("createdBy",user.id)
                    data.put("updatedBy",user.id)

                    sqlSessionFactory.openSession().insert("ApprovalChainTransactionMapper.insertSaveMonthlyToSpecial",data)

                }

                isSaved = true;
                ErrorMsg = "Plan changed from Monthly to Special Successfully...";





            }

        } else{

            var month1 = plan.month?.toInt()
            var year1 = plan.year?.toInt()

            var dip = plan.planId?.let { getDispatchPlanById(it) }

            if(dip == null ){
                var data: MutableMap<String, Any> = mutableMapOf()
                month1?.let { data.put("month", it.toInt()) }
                year1?.let { data.put("year", it.toInt()) }
                data.put("isSpecial", 0)
                data.put("updatedBy",user.id)

                sqlSessionFactory.openSession().update("DispatchPlanMapper.saveMonthlyToMonthly",data)

            } else {
                isSaved = false;
                ErrorMsg = "Error occured as Already Have Plan for the Month..";
            }


        }






    }




    // SPECIAL APPROVAL



    fun getSpecialPlanForApproval(month: Int, year: Int, userId: String, userDesgId: String): List<MontlyApprovalBexDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("Month", month)
        data.put("Year", year)
        data.put("UserID", userId)
        data.put("UserDesignation", userDesgId)

        var plans: List<MontlyApprovalBexDTO> = ArrayList<MontlyApprovalBexDTO>()
        if(userDesgId == UserLovEnum.BEX.id){
             plans =  sqlSessionFactory.openSession().selectList<MontlyApprovalBexDTO?>("ApprovalMapper.getSpecialApprovalForBex", data).toList()
        }
        if(userDesgId == UserLovEnum.BUH.id){
            plans =  sqlSessionFactory.openSession().selectList<MontlyApprovalBexDTO?>("ApprovalMapper.getSpecialApprovalForBuh", data).toList()
        }

        return plans


    }


    fun getSpecialPlanApprovalDetails(planId: String): List<SpecialAllocationDetailsForApprovalDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("DipID", planId)

        var specialAllocationDetails = mutableListOf<SpecialAllocationDetailsForApprovalDTO>()

        specialAllocationDetails =  sqlSessionFactory.openSession().selectList<SpecialAllocationDetailsForApprovalDTO>("ApprovalMapper.getSpecialPlanApprovalDetails",data)

        var filteredSpecialAllocation = specialAllocationDetails.filter { (it.quantity ?: 0) > 0 }.toMutableList()

        return filteredSpecialAllocation
    }


    // VIRTUAL APPROVAL


    fun getVirtualPlanForApproval(month: Int, year: Int, userId: String, userDesgId: String): List<MontlyApprovalBexDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("Month", month)
        data.put("Year", year)
        data.put("UserID", userId)
        data.put("UserDesignation", userDesgId)



            return sqlSessionFactory.openSession().selectList<MontlyApprovalBexDTO?>("ApprovalMapper.getVirtualApprovalForBex", data).toList()


    }


    fun getVirtualPlanApprovalDetails(planId: String): List<SpecialAllocationDetailsForApprovalDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("DipID", planId)


        return sqlSessionFactory.openSession().selectList("ApprovalMapper.getVirtualPlanApprovalDetails",data)
    }


      fun virtualAllocationDownload(vrl: List<VirtualAllocationDownloadDTO>):List<VirtualAllocationDetailsDTO> {

          val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
          var virtualAllocationDetails = mutableListOf<VirtualAllocationDetailsDTO>()
          var finalList = mutableListOf<VirtualAllocationDetailsDTO>()
          vrl.forEach {
              var data: MutableMap<String, Any> = mutableMapOf()
              data.put("month",it.month!!)
              data.put("year",it.year!!)
              data.put("planid",it.planId!!)

              virtualAllocationDetails = sqlSessionFactory.openSession().selectList<VirtualAllocationDetailsDTO>("ApprovalMapper.virtualAllocationDownload",data)

              finalList.addAll(virtualAllocationDetails)


          }

        return finalList
    }













}