package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.*
import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.InventoryDTO
import com.squer.promobee.controller.dto.InventoryReversalDTO
import com.squer.promobee.mapper.InventoryMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.*
import org.apache.ibatis.jdbc.SQL
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.time.ZoneId
import java.util.*
import kotlin.math.max


@Repository
class InventoryRepository @Autowired constructor(
        securityUtility: SecurityUtility
): BaseRepository<Inventory>(
        securityUtility = securityUtility
){

    @Autowired
    lateinit var inventoryMapper: InventoryMapper

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


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


    fun searchInventory(  isExhausted: Boolean, isPopup:Int) : List<InventoryDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()
//        data.put("name", name)
        data.put("isExhausted", isExhausted)
        data.put("isPopup", isPopup)
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


    fun reverseInventory(inv: InventoryReversalDTO) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        inv.invId?.let { data.put("id", it) }
        inv.quantity?.let { data.put("quantity", it) }
        inv.remarks?.let { data.put("remarks", it) }



        var inv1 = Inventory()


        if(inv1.qtyAllocated != null && inv1.qtyAllocated!! > 0 && inv.quantity!! > ((inv1.qtyReceived?.minus((inv1.qtyAllocated!! + inv1.qtyDispatched!!!!))).toString())){

            inv.invId?.let { data.put("id", it) }

             sqlSessionFactory.openSession().selectList<InventoryReversalDTO>("InventoryMapper.getInventoryAllocatedByBM",data)

        }



        var data0: MutableMap<String, Any> = mutableMapOf()

         var inqd = inv.qtyDispatched?.plus(inv.quantity?.toInt()!!)

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

        var planId1 = UUID.randomUUID().toString()

        data1.put("id",planId1)
        data1.put("owner",user.id)
        data1.put("month",month)
        data1.put("year",year)
//        data1.put("planStatus", NamedSquerEntity(plan.planStatus?.id.toString(),""))
        data1.put("planStatus",MonthlyPlanStatusEnum.APPROVED_ID)
//        plan.submissionDate?.let { data1.put("submissionDate", it) }
//        plan.approvalDate?.let { data1.put("approvalDate", it) }
        data1.put("isSpecial", 1)
        data1.put("remarks",reversalRemarks)
        data1.put("createdBy", user.id)
        data1.put("updatedBy", user.id)
        data1.put("invoiceStatus",DispatchPlanInvoiceStatus.FULLY_INVOICED)

        sqlSessionFactory.openSession().insert("DispatchPlanMapper.insertReversalPlan",data1)



        //dispatch detail reversal

        var planDetail = DispatchDetail()

        var data2: MutableMap<String, Any> = mutableMapOf()

        var planDetailId1 = UUID.randomUUID().toString()

        data2.put("id",planDetailId1)
        data2.put("planId",planId1)
        inv.invId?.let { data2.put("inventoryId", it) }
        data2.put("recipientId",RecipientEnum.HUB_MANAGER)
        inv.quantity?.let { data2.put("qtyDispatch", it) }
        data2.put("quarterlyPlanId",QtpEnum.QTP)
        data2.put("detailStatus",DispatchDetailStatusEnum.INVOICED)
        data2.put("createdBy", user.id)
        data2.put("updatedBy", user.id)

        sqlSessionFactory.openSession().insert("DispatchDetailMapper.insertReversalPlanDetail",data2)

        //invoice header reversal

        var invoiceHeader = InvoiceHeader();

        var data3: MutableMap<String, Any> = mutableMapOf()

        var inhId1 = UUID.randomUUID().toString()

        data3.put("id",inhId1)
        invoiceHeader.invoiceNo?.let { data3.put("invoiceNo", it) }
        data3.put("type",InvoiceTypeEnum.REVERSAL)
        data3.put("statusId",InvoiceStatusEnum.GENERATED_PRINTED)
        data3.put("teamId", TeamEnum.HUB_TEAM)
        data3.put("recipientId", RecipientEnum.HUB_MANAGER)
        invoiceHeader.addressLine1?.let { data3.put("addressLine1", it) }
        invoiceHeader.states?.let { data3.put("states", it) }
        invoiceHeader.city?.let { data3.put("city", it) }
        invoiceHeader.zip?.let { data3.put("zip", it) }
        data3.put("notes",ReversalRemarkEnum.PRUNED)
        data3.put("transporterId",TeamEnum.HUB_TRANSPORTER)
        data3.put("createdBy", user.id)
        data3.put("updatedBy", user.id)
        data3.put("designationId",ReversalRemarkEnum.PRUNED)

        sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertReversalInvoiceHeader",data3)

        var invoiceDetail = InvoiceDetail()

        var data4: MutableMap<String, Any> = mutableMapOf()

        var indId1 = UUID.randomUUID().toString()

        var inQNTY : String? = inv.quantity
        var inRPU : Double? = inv1.ratePerUnit

        var inQNTY1 : Int? = inQNTY as Int?
        var inRPU1 : Int? = inRPU as Int?

        var valueResult = inRPU1?.let { inQNTY1?.times(it) }




        data4.put("id",indId1)
        data4.put("headerId",inhId1)
        inv1.item?.let { data4.put("itemId", it) }
        inv.quantity?.let { data4.put("quantity", it) }
        data4.put("didId",planDetailId1)
        valueResult?.let { data4.put("value", it) }
        data4.put("createdBy", user.id)
        data4.put("updatedBy", user.id)
        inv.invId?.let { data4.put("inventoryId", it) }

        sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertReversalInvoiceDetail",data4)


        var idp = InvoiceDetailPlan();

        var data5: MutableMap<String, Any> = mutableMapOf()

        data5.put("id", UUID.randomUUID().toString())
        data5.put("headerId",inhId1)
        data5.put("planId",planId1)

        sqlSessionFactory.openSession().insert("InvoiceDetailPlanMapper.insertReversalIDP",data5)




    }




}









