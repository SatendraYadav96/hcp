package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.*
import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.*
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.time.ZoneId
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Repository
class InventoryRepository @Autowired constructor(
    securityUtility: SecurityUtility,
): BaseRepository<Inventory>(
        securityUtility = securityUtility
){


    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    @Autowired
    lateinit var dispatchPlanRepository:DispatchPlanRepository


    fun insertInventory(inventory: Inventory){
       sqlSessionFactory.openSession().insert("InventoryMapper.insertInventory", inventory)
    }

    fun getMonthlyAllocation(planId: String, userId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO> {
        var data : MutableMap<String, String> = mutableMapOf()
        data.put("planId", planId)
        data.put("userId", userId)
        return sqlSessionFactory.openSession().selectList<AllocationInventoryDetailsWithCostCenterDTO>("InventoryMapper.getAllocationInventoryWithCostcenter", data)
    }

    fun getInventoryDistributionByTeamForPlan(planId: String): List<MutableMap<String, Any>>{
        return sqlSessionFactory.openSession().selectList("InventoryMapper.planDistributionForItemSelect", planId)
    }


    fun editUnitAllocation(inv: InventoryDTO) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        inv.invId?.let { data.put("id", it) }
        inv.isUnitAllocation?.let { data.put("isUnitAllocation", it) }

        data.put("updatedBy", user.id)

        sqlSessionFactory.openSession().update("InventoryMapper.editUnitAllocation",data)


    }



    fun blockItem(inv: InventoryDTO) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        inv.invId?.let { data.put("id", it) }
        inv.isBlockItem?.let { data.put("isBlockItem", it) }

        data.put("updatedBy", user.id)

        sqlSessionFactory.openSession().update("InventoryMapper.blockItem",data)


    }


    fun searchInventory(  isExhausted: Int) : List<InventoryDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
//        data.put("name", name)
        data.put("isExhausted", isExhausted)
//        data.put("isPopup", isPopup)
        return sqlSessionFactory.openSession().selectList("InventoryMapper.searchInventory", data)
    }


    fun getInventoryReversalHistory(  invId: String) : List<InventoryReversalDTO> {
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("id", invId)

         return sqlSessionFactory.openSession().selectList("InventoryMapper.getInventoryReversalHistory", data)
    }

    fun getInventoryReversalBMAllocation(  invId: String) : List<InventoryReversalDTO> {
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id", invId)

        return sqlSessionFactory.openSession().selectList("InventoryMapper.getInventoryAllocatedByBM", data)
    }


    fun getInventoryById(  invId: String): Inventory{

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id", invId)

        return sqlSessionFactory.openSession().selectOne("InventoryMapper.getInventoryById", data)
    }

    fun getMaxInvoiceNo(  ) : Int?  {

        var data: MutableMap<String, Any> = mutableMapOf()

//        data.put("id", invId)

        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getMaxInvoiceNo")
    }



    fun reverseInventory(inv: InventoryReversalDTO)  {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        inv.invId?.let { data.put("id", it) }
        inv.quantity?.let { data.put("quantity", it) }
        inv.remarks?.let { data.put("remarks", it) }



        val inv1 = inv.invId?.let { getInventoryById(it) }
         var itemId = inv1?.item?.id







        if(inv1!!.qtyAllocated != null && inv1.qtyAllocated!! > 0 && inv.quantity!! > ((inv1.qtyReceived?.minus((inv1.qtyAllocated!! + inv1.qtyDispatched!!!!))).toString())){

            inv.invId?.let { data.put("id", it) }

             sqlSessionFactory.openSession().selectList<InventoryReversalDTO>("InventoryMapper.getInventoryAllocatedByBM",data)

        }



        var data0: MutableMap<String, Any> = mutableMapOf()

//        var inv2 =  InventoryService.getInventoryById(inv.invId)

        var qtyDisp = inv1.qtyDispatched?.absoluteValue



         var inqd = qtyDisp?.plus(inv.quantity?.toInt()!!)





//        var inqd = inv1.qtyDispatched?.plus(inv.quantity!!)

//        var inqd = inv1.qtyDispatched






        inv.invId?.let { data0.put("id", it) }
//        inv.quantity?.let { data0.put("qtyDispatched", it) }
        inqd?.let { data0.put("qtyDispatched", it) }
        data0.put("updatedBy", user.id)

        sqlSessionFactory.openSession().update("InventoryMapper.inventoryUpdateQtyDispatch",data0)


        // dispatch plan reversal

        var plan = DispatchPlan()
        var reversalRemarks = "Inventory Reversal"

        val date = Date()
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth

        var data1: MutableMap<String, Any> = mutableMapOf()

        val planId1 = UUID.randomUUID().toString()

        data1.put("id",planId1)
        data1.put("owner",user.id)
        data1.put("month",month)
        data1.put("year",year)
//        data4.put("planStatus", NamedSquerEntity(plan.planStatus?.id.toString(),""))
        data1.put("planStatus",MonthlyPlanStatusEnum.APPROVED_ID.id)
//        plan.submissionDate?.let { data4.put("submissionDate", it) }
//        plan.approvalDate?.let { data4.put("approvalDate", it) }
        data1.put("isSpecial", 1)
        data1.put("remarks",reversalRemarks)
        data1.put("createdBy", user.id)
        data1.put("updatedBy", user.id)
        data1.put("invoiceStatus",DispatchPlanInvoiceStatus.FULLY_INVOICED.id)

        sqlSessionFactory.openSession().insert("DispatchPlanMapper.insertReversalPlan",data1)



        //dispatch detail reversal

        var planDetail = DispatchDetail()

        var data2: MutableMap<String, Any> = mutableMapOf()

        val planDetailId1 = UUID.randomUUID().toString()

        data2.put("id",planDetailId1)
        data2.put("planId",planId1)
        inv.invId?.let { data2.put("inventoryId", it) }
        data2.put("recipientId",RecipientEnum.HUB_MANAGER.id)
        inv.quantity?.let { data2.put("qtyDispatch", it) }
        data2.put("quarterlyPlanId",QtpEnum.QTP.id)
        data2.put("detailStatus",DispatchDetailStatusEnum.INVOICED.id)
        data2.put("createdBy", user.id)
        data2.put("updatedBy", user.id)

        sqlSessionFactory.openSession().insert("DispatchDetailMapper.insertReversalPlanDetail",data2)

        //invoice header reversal

        var invoiceHeader = InvoiceHeader();

        var data3: MutableMap<String, Any> = mutableMapOf()

        val inhId1 = UUID.randomUUID().toString()

//        var invNo = getMaxInvoiceNo()
//        var invNo1 = 1.plus(invNo)


        data3.put("id",inhId1)
        invoiceHeader.invoiceNo?.let { data3.put("invoiceNo", it) }
//        data3.put("invoiceNo",invNo1)
        data3.put("type",InvoiceTypeEnum.REVERSAL.id)
        data3.put("statusId",InvoiceStatusEnum.GENERATED_PRINTED.id)
        data3.put("teamId", TeamEnum.HUB_TEAM.id)
        data3.put("recipientId", RecipientEnum.HUB_MANAGER.id)
        invoiceHeader.addressLine1?.let { data3.put("addressLine1", it) }
        invoiceHeader.states?.let { data3.put("states", it) }
        invoiceHeader.city?.let { data3.put("city", it) }
        invoiceHeader.zip?.let { data3.put("zip", it) }
        data3.put("notes",ReversalRemarkEnum.PRUNED.name)
        data3.put("transporterId",TeamEnum.HUB_TRANSPORTER.id)
        data3.put("createdBy", user.id)
        data3.put("updatedBy", user.id)
        data3.put("designationId",ReversalRemarkEnum.PRUNED.id)

        sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertReversalInvoiceHeader",data3)




        var invoiceDetail = InvoiceDetail()

        var data4: MutableMap<String, Any> = mutableMapOf()

        val indId1 = UUID.randomUUID().toString()

//        var inQNTY : String? = inv.quantity
//        var inRPU : Double? = inv1.ratePerUnit
//
//        var inQNTY1 : Int? = inQNTY as Int?
//        var inRPU1 : Int? = inRPU as Int?
//
//        var valueResult = inRPU1?.let { inQNTY1?.times(it) }

        var valueResult = inv.quantity?.let { inv1.ratePerUnit?.times(it.toDouble()) }





        inhId1

        data4.put("id",indId1)
        data4.put("headerId",inhId1)
        inv1.item?.let { data4.put("item", it.id) }
        inv.quantity?.let { data4.put("quantity", it) }
        data4.put("didId",planDetailId1)
        valueResult?.let { data4.put("value", it) }
        data4.put("createdBy", user.id)
        data4.put("updatedBy", user.id)
        inv.invId?.let { data4.put("inventoryId", it) }

        sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertReversalInvoiceDetail",data4)


        var idp = InvoiceDetailPlan();

        var data6: MutableMap<String, Any> = mutableMapOf()

        inhId1


        data6.put("id", UUID.randomUUID().toString())
        data6.put("headerId",inhId1)
        data6.put("planId",planId1)

        sqlSessionFactory.openSession().insert("InvoiceDetailPlanMapper.insertReversalIDP",data6)


    }

    fun switchInventory(inv: SwitchInventoryDTO){

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data0: MutableMap<String, Any> = mutableMapOf()

        var fromInv = inv.fromInvId?.let { getInventoryById(it) }

//        val a = fromInv?.qtyReceived!! - fromInv?.qtyDispatched!!


//        if(a < inv.quantity?.toInt()!!){
//
//            println("Insufficient Qty to Swap")
//        }

        var switchQtyDispatched = fromInv?.qtyDispatched!! + inv?.quantity!!.toInt()



        inv.fromInvId?.let { data0.put("id", it) }
         data0.put("qtyDispatched", switchQtyDispatched)
        data0.put("updatedBy", user.id)

         sqlSessionFactory.openSession().update("InventoryMapper.switchInventoryFromQtyDispatch",data0)


//        var maxInvNo: Int?  = getMaxInvoiceNo()?.plus(1)

        var inh = InvoiceHeader()

        var data1: MutableMap<String, Any> = mutableMapOf()

        var inhId = UUID.randomUUID().toString()

        data1.put("id",inhId)
        inh.invoiceNo?.let { data1.put("invoiceNo", it) }

        data1.put("type",InvoiceTypeEnum.SWITCH.id)
        data1.put("statusId",InvoiceStatusEnum.GENERATED_PRINTED.id)
        data1.put("teamId", TeamEnum.HUB_TEAM.id)
        data1.put("recipientId", RecipientEnum.HUB_MANAGER.id)
        inh.addressLine1?.let { data1.put("addressLine1", it) }
        inh.states?.let { data1.put("states", it) }
        inh.city?.let { data1.put("city", it) }
        inh.zip?.let { data1.put("zip", it) }
        inv.remarks?.let { data1.put("notes", it) }
        data1.put("transporterId",TeamEnum.HUB_TRANSPORTER.id)
        data1.put("createdBy", user.id)
        data1.put("updatedBy", user.id)


        sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertSwitchInvoiceHeader",data1)

        var ind = InvoiceDetail()

        var data2: MutableMap<String, Any> = mutableMapOf()

        var indId = UUID.randomUUID().toString()

//        var inQNTY : String? = inv.quantity
//        var inRPU : Double? = inv1.ratePerUnit
//
//        var inQNTY1 : Int? = inQNTY as Int?
//        var inRPU1 : Int? = inRPU as Int?
//
//        var valueResult = inRPU1?.let { inQNTY1?.times(it) }

        var valueResult = inv.quantity?.let { fromInv.ratePerUnit?.times(it.toDouble())?.roundToInt()}






        data2.put("id",indId)
        data2.put("headerId",inhId)
        fromInv.item?.let { data2.put("item", it.id) }
        inv.quantity?.let { data2.put("quantity", it) }
//        data6.put("didId",planDetailId1)
        valueResult?.let { data2.put("value", it) }
        data2.put("createdBy", user.id)
        data2.put("updatedBy", user.id)
        inv.fromInvId?.let { data2.put("inventoryId", it) }

        sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertSwitchInvoiceDetail",data2)


        var data3: MutableMap<String, Any> = mutableMapOf()


        var toInv = inv.toInvId?.let { getInventoryById(it) }

        var switchQtyDispatched1 = toInv?.qtyDispatched!! - inv?.quantity!!.toInt()


        inv.toInvId?.let { data3.put("id", it) }
        data3.put("qtyDispatched", switchQtyDispatched1)
        data3.put("updatedBy", user.id)

         sqlSessionFactory.openSession().update("InventoryMapper.switchInventoryToQtyDispatch",data3)


//        var maxInvNo1: Int?  = getMaxInvoiceNo()?.plus(1)

        var inh1 = InvoiceHeader()

        var data4: MutableMap<String, Any> = mutableMapOf()

        var inhId1 = UUID.randomUUID().toString()

        data4.put("id",inhId1)
        inh1.invoiceNo?.let { data4.put("invoiceNo", it) }

        data4.put("type",InvoiceTypeEnum.SWITCH.id)
        data4.put("statusId",InvoiceStatusEnum.GENERATED_PRINTED.id)
        data4.put("teamId", TeamEnum.HUB_TEAM.id)
        data4.put("recipientId", RecipientEnum.HUB_MANAGER.id)
        inh1.addressLine1?.let { data4.put("addressLine1", it) }
        inh1.states?.let { data4.put("states", it) }
        inh1.city?.let { data4.put("city", it) }
        inh1.zip?.let { data4.put("zip", it) }
        inv.remarks?.let { data4.put("notes", it) }
        data4.put("transporterId",TeamEnum.HUB_TRANSPORTER.id)
        data4.put("createdBy", user.id)
        data4.put("updatedBy", user.id)


        sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertSwitchInvoiceHeader1",data4)

        var ind1 = InvoiceDetail()

        var data5: MutableMap<String, Any> = mutableMapOf()

        var indId1 = UUID.randomUUID().toString()

//        var inQNTY : String? = inv.quantity
//        var inRPU : Double? = inv1.ratePerUnit
//
//        var inQNTY1 : Int? = inQNTY as Int?
//        var inRPU1 : Int? = inRPU as Int?
//
//        var valueResult = inRPU1?.let { inQNTY1?.times(it) }

        var valueResult1 = inv.quantity?.let { toInv.ratePerUnit?.times(it.toDouble())?.roundToInt()  }






        data5.put("id",indId1)
        data5.put("headerId",inhId1)
        toInv.item?.let { data5.put("item", it.id) }
        inv.quantity?.let { data5.put("quantity", it) }
//        data6.put("didId",planDetailId1)
        valueResult1?.let { data5.put("value", it) }
        data5.put("createdBy", user.id)
        data5.put("updatedBy", user.id)
        inv.toInvId?.let { data5.put("inventoryId", it) }

        sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertSwitchInvoiceDetail1",data5)












    }



    fun getPickList(teamId: String, month: Int, year: Int, isSpecial: Int): List<PickListDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String, String> = mutableMapOf()
        data.put("TEAM_ID", teamId)
        data.put("MONTH", month.toString())
        data.put("YEAR", year.toString())
        data.put("Special", isSpecial.toString())

        return sqlSessionFactory.openSession().selectList<PickListDTO>("DispatchInvoicingMapper.getPickList", data)
    }


    fun getPickListVirtual(teamId: String, month: Int, year: Int, isSpecial: Int): List<PickListDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data0 : MutableMap<String, String> = mutableMapOf()
        data0.put("TEAM_ID", teamId)
        data0.put("MONTH", month.toString())
        data0.put("YEAR", year.toString())
        data0.put("Special", isSpecial.toString())

        return sqlSessionFactory.openSession().selectList<PickListDTO>("DispatchInvoicingMapper.getPickListVirtual", data0)
    }

    fun getPickListStatusByBM(teamId: String, month: Int, year: Int): List<BrandManagerPlanStatusDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data : MutableMap<String, String> = mutableMapOf()
        data.put("TeamID", teamId)
        data.put("Month", month.toString())
        data.put("Year", year.toString())


        return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getPickListStatusByBM", data)
    }


    fun getSpecialDispatchListForInvoicing(planId: String, status: String): List<DataModelInvoiceDetailsDTO> {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, String> = mutableMapOf()
        data.put("Plan_ID", planId)
        data.put("StatusSLV", status)

        if (status == InvoiceStatusEnum.GENERATED_PRINTED.id || status == InvoiceStatusEnum.CANCELLED.id || status == InvoiceStatusEnum.REDIRECTED.id) {

            return sqlSessionFactory.openSession()
                .selectList("DispatchInvoicingMapper.getSpecialDispatchInvoicingListForGeneratedPrinted", data)


        } else {
            return sqlSessionFactory.openSession()
                .selectList("DispatchInvoicingMapper.getSpecialDispatchInvoicingListForDraft", data)

        }


    }




        fun getVirtualDispatchListForInvoicing(planId: String, status: String): List<DataModelInvoiceDetailsDTO> {
            val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

            var data : MutableMap<String, String> = mutableMapOf()
            data.put("Plan_ID", planId)
            data.put("StatusSLV", status)

            if(status == InvoiceStatusEnum.GENERATED_PRINTED.id || status == InvoiceStatusEnum.CANCELLED.id || status == InvoiceStatusEnum.REDIRECTED.id){

                return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getVirtualDispatchInvoicingListForGeneratedPrinted", data)


            }else {
                return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getVirtualDispatchInvoicingListForDraft", data)

            }

        }




    fun getEmployeeInvoicePopupDetails(month: Int, year: Int, isSpecial: Int, invoiceHeaderId: String, employeeId: String): List<EmployeeInvoiceDetailsPopupDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data : MutableMap<String, String> = mutableMapOf()
        data.put("Month", month.toString())
        data.put("Year", year.toString())
        data.put("IsSpecial", isSpecial.toString())
        data.put("RecipientID", employeeId)

        if(invoiceHeaderId == "0") {


            var data1 : MutableMap<String, String> = mutableMapOf()
            data1.put("Month", month.toString())
            data1.put("Year", year.toString())
            data1.put("IsSpecial", isSpecial.toString())
            data1.put("RecipientID", employeeId)

            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getEmployeeDraftedInvoicePopupDetails",data1)


        }else {

            var data0 : MutableMap<String, String> = mutableMapOf()
            data0.put("InvoiceHeaderID", invoiceHeaderId)


            return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getEmployeeGeneratedInvoicePopupDetails",data0)
        }


    }


    fun exportAllocation(year: Int, month: Int, teamId: String, status: String, isSpecial: Int, planId: String, isVirtual: Int): List<DataModelInvoiceDetailsDTO>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data : MutableMap<String, String> = mutableMapOf()


        val exportData: List<DataModelInvoiceDetailsDTO> = ArrayList()



       if(isSpecial == 0){
           data.put("year", year.toString())
           data.put("month", month.toString())
           data.put("isSpecialDisp", isSpecial.toString())
           data.put("teamId",teamId)
           data.put("status","00000000-0000-0000-0000-000000000024")

           if(status == InvoiceStatusEnum.GENERATED_PRINTED.id || status == InvoiceStatusEnum.CANCELLED.id || status == InvoiceStatusEnum.REDIRECTED.id){
        exportData =        return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.GetEmployeeInvoiceDetail_GP_C_R", data)
           }else{
               exportData =    return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getEmployeeInvoiceDetailDraft", data)
           }
       }
        else if (isVirtual == 1){

           data.put("Plan_ID", planId)
           data.put("StatusSLV", "00000000-0000-0000-0000-000000000024")

           if(status == InvoiceStatusEnum.GENERATED_PRINTED.id || status == InvoiceStatusEnum.CANCELLED.id || status == InvoiceStatusEnum.REDIRECTED.id){

               exportData =     return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getVirtualDispatchInvoicingListForGeneratedPrinted", data)


           }else {
               exportData =  return sqlSessionFactory.openSession().selectList("DispatchInvoicingMapper.getVirtualDispatchInvoicingListForDraft", data)

           }


        }

        else{

           data.put("Plan_ID", planId)
           data.put("StatusSLV", "00000000-0000-0000-0000-000000000024")

           if (status == InvoiceStatusEnum.GENERATED_PRINTED.id || status == InvoiceStatusEnum.CANCELLED.id || status == InvoiceStatusEnum.REDIRECTED.id) {

               exportData = return sqlSessionFactory.openSession()
                   .selectList("DispatchInvoicingMapper.getSpecialDispatchInvoicingListForGeneratedPrinted", data)


           } else {
               exportData =   return sqlSessionFactory.openSession()
                   .selectList("DispatchInvoicingMapper.getSpecialDispatchInvoicingListForDraft", data)

           }

        }









    }




}

















