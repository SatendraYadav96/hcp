package com.squer.promobee.mapper

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.persistence.PersistenceConfig
import com.squer.promobee.persistence.SearchCriteria
import com.squer.promobee.service.repository.domain.Inventory
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultType
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.session.ExecutorType
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
import org.mybatis.spring.boot.autoconfigure.MybatisProperties
import org.springframework.beans.factory.annotation.Autowired

@Mapper
interface InventoryMapper: BaseMapper<Inventory> {

   /* val sqlSessionFactory: SqlSessionFactory

    @Insert("insert into INVENTORY_INV (ID_INV, ID_ITM_INV, ID_GRN_INV, PACK_SIZE_INV, PO_NO_INV, ID_CCM_INV, LMID_INV, POSTING_DATE_INV, EXPIRY_DATE_INV, ID_ITC_INV, MEDICAL_CODE_INV, ID_VND_INV, RATE_PER_UNIT_INV, QTY_RECEIVED_INV, QTY_ALLOCATED_INV, QTY_DISPATCHED_INV, NUM_BOXES_INV," +
            " IS_UNIT_ALLOCATION_INV, BATCH_NO_INV, CREATED_ON_INV, CREATED_BY_INV, UPDATED_ON_INV, UPDATED_BY_INV, HSN_CODE_INV, RATE_PER_INV, UNITS_INV) values(#{id}, #{item.id}, #{grn.id}, #{packSize}, #{poNo}, #{ccmID.id}, #{limid}, #{postingDate}, #{expiryDate}, #{categoryId.id}, #{medicalCode}, #{vendorId.id}," +
            " #{ratePerUnit}, #{qtyReceived}, #{qtyAllocated}, #{qtyDispatched}, #{numBoxes}, #{unitAllocation}, #{batchNo}, GETDATE(), #{createdBy}, GETDATE(), #{updatedBy}, #{hsnCode}, #{rate}, #{units})")
    fun insertInventory(inventory: Inventory)*/
    
   /* @Select("<script>select" +
            "        inv.ID_ITM_INV, smp.NAME_SMP as ITM_NAME, inv.ID_ITC_INV, sum(inv.QTY_RECEIVED_INV) as QTY_RECEIVED," +
            "        sum(inv.QTY_ALLOCATED_INV) as QTY_ALLOCATED, sum(inv.QTY_DISPATCHED_INV) as QTY_DISPATCHED, avg(inv.PACK_SIZE_INV) as PACK_SIZE," +
            "        NULL as EXPIRY_DATE, NULL AS INVENTORY_ID" +
            "        from" +
            "        INVENTORY_INV inv" +
            "        inner join SAMPLE_MASTER_SMP smp on smp.ID_SMP=inv.ID_ITM_INV and inv.ID_ITC_INV='242B921C-B27A-497D-9FA1-7222AB6E6F2A' and smp.IS_ACTIVE_SMP=1" +
            "        inner join (" +
            "        select distinct brd.ID_BRD as ID_BRD" +
            "        from BRAND_MASTER_BRD brd" +
            "        inner join BRANDMANAGER_BRAND_BBR bbr on brd.ID_BRD = bbr.ID_BRD_BBR AND brd.IS_ACTIVE_BRD = 1" +
            "        where bbr.ID_USR_BBR = #{userId}" +
            "        ) a on a.ID_BRD = smp.ID_BRD_SMP" +
            "        <where>" +
            "            DATEDIFF(day, GETDATE(), inv.EXPIRY_DATE_INV) &gt;= 180" +
            "        </where>" +
            "        group by inv.ID_ITM_INV, inv.ID_ITC_INV, smp.NAME_SMP" +
            "        union all" +
            "        select" +
            "        inv.ID_ITM_INV, itm.NAME_ITM as ITM_NAME, inv.ID_ITC_INV, sum(inv.QTY_RECEIVED_INV) as QTY_RECEIVED," +
            "        sum(inv.QTY_ALLOCATED_INV) as QTY_ALLOCATED, sum(inv.QTY_DISPATCHED_INV) as QTY_DISPATCHED, avg(inv.PACK_SIZE_INV) as PACK_SIZE," +
            "        NULL as EXPIRY_DATE, NULL AS INVENTORY_ID" +
            "        from" +
            "        INVENTORY_INV inv" +
            "        inner join ITEM_MASTER_ITM itm on itm.ID_ITM=inv.ID_ITM_INV and itm.IS_ACTIVE_ITM=1 and inv.ID_ITC_INV!='242B921C-B27A-497D-9FA1-7222AB6E6F2A'" +
            "        inner join COST_CENTER_MASTER_CCM ccm on ccm.ID_CCM=inv.ID_CCM_INV and IS_ACTIVE_CCM=1" +
            "        inner join (" +
            "        select distinct cbr.ID_CCM_CBR as ID_CCM_CBR from COSTCENTER_BRAND_CBR cbr" +
            "        inner join BRAND_MASTER_BRD brd on cbr.ID_BRD_CBR = brd.ID_BRD and brd.IS_ACTIVE_BRD=1" +
            "        inner join BRANDMANAGER_BRAND_BBR bbr on brd.ID_BRD = bbr.ID_BRD_BBR" +
            "        where bbr.ID_USR_BBR=#{userId}" +
            "        ) a on a.ID_CCM_CBR=ccm.ID_CCM" +
            "        <where>" +
            "            DATEDIFF(day, GETDATE(), inv.EXPIRY_DATE_INV)&gt;=30" +
            "        </where>" +
            "        group by inv.ID_ITM_INV, inv.ID_ITC_INV, itm.NAME_ITM</script>")
    @ResultType(HashMap::class)*/
    /*fun getMonthlyAllocation(planId: String,userId: String):List<AllocationInventoryDetailsWithCostCenterDTO>{

    }*/


}