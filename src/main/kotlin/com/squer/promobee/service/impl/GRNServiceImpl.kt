package com.squer.promobee.service.impl

import com.squer.promobee.api.v1.enums.ItemCategoryEnum
import com.squer.promobee.controller.dto.GRNAckDTO
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.SquerEntity
import com.squer.promobee.security.util.DateUtils.Companion.addDays
import com.squer.promobee.service.*
import com.squer.promobee.service.repository.GRNRepository
import com.squer.promobee.service.repository.domain.Inventory
import com.squer.promobee.service.repository.domain.Item
import com.squer.promobee.service.repository.domain.Vendor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class GRNServiceImpl @Autowired constructor(
        private val grnRepository: GRNRepository,
        private val itemService: ItemService,
        private val itemCategoryService: ItemCategoryService,
        private val sampleMasterService: SampleMasterService,
        private val costCenterService: CostCenterService,
        private val vendorService: VendorService,
        private val inventoryService: InventoryService
): GrnService {



    private val log = LoggerFactory.getLogger(javaClass)

    override fun getUnacknowledgeData() : MutableMap<String, Any> {
        var result : MutableMap<String, Any> = HashMap()
        //list of UnacknowledgeGrns
        val grns = grnRepository.getUnacknowledgeData()

        //MedicalCode Count
        var medicalItemcount: Int

         val medicalItem = itemService.getMaxItemCount("%M%")
        if(medicalItem !== null){
            medicalItemcount = (medicalItem.substring(1).toInt()) + 1
        }
        else{
            medicalItemcount = 1
        }
        println(medicalItemcount)

        //Non-MedicalCode Count
        var nonMedicalItemcount: Int
        val nonMedicalItem = itemService.getMaxItemCount("%N%")
        if(nonMedicalItem !== null){
            nonMedicalItemcount = (nonMedicalItem.substring(1).toInt()) + 1
        }
        else{
            nonMedicalItemcount = 1
        }
        println(nonMedicalItemcount)

        //Item Category List
        var categoryList: MutableMap<String, String?> = HashMap()
        val category = itemCategoryService.getItemCategory()
        category.forEach{
            categoryList[it.id] = it.name
        }

        println(categoryList)

        var itemCategory: MutableMap<String, String?> = HashMap()
        ItemCategoryEnum.values().forEach {
            itemCategory[it.name] = it.id
        }

        //updating GRNs
        grns.forEach {

            //set Item category
            if(it.limid !== null){
                it.category = NamedSquerEntity(ItemCategoryEnum.SAMPLES.id, ItemCategoryEnum.SAMPLES.name)
            }
            else if(it.lineText!!.isEmpty()){


                it.category = NamedSquerEntity( ItemCategoryEnum.NON_MEDICAL.id, ItemCategoryEnum.NON_MEDICAL.name)
            }
            else {

                it.category = NamedSquerEntity(ItemCategoryEnum.MEDICAL.id, ItemCategoryEnum.MEDICAL.name)
            }

            //set Cost Center
            if(it.limid != null ){
                var lmid = it.limid!!.lowercase(Locale.getDefault())
                it.costCenter = sampleMasterService.getSampleName(lmid)["NAME_BRD"] as String?
            }



            else{
                it.costCenter = costCenterService.getCostCenterNameById(it.ccmId?.id)["NAME_CCM"] as String?
            }
            //Calculate Expiry Date
            if(it.expiryDate == null){
                if(it.lineText == null){
                    val itemcategory = itemCategoryService.getItemCategoryById(it.category!!.id)
                    val days = itemcategory.expireAfterDays
                    it.expiryDate = addDays(it.postingDate!!, days!!)
                }
            }
            //Cost Center Code
            if(it.ccmId !== null){
                it.costCenterCode = costCenterService.getCostCenterCodeById(it.ccmId!!.id)["CODE_CCM"].toString()
            }
            else{
                it.costCenterCode = ""
            }
            //Calculate Value
            if(it.category!!.id === ItemCategoryEnum.SAMPLES.id){
                it.value = it.qty!! * it.ratePerUnit!!
            }
        }
        result.put("grn", grns)
        result.put("medicalItemCount", medicalItemcount)
        result.put("nonMedicalItemCount", nonMedicalItemcount)
        result.put("categoryList", categoryList)
        result.put("itemCategory", itemCategory)
        return result
    }

    override fun rejectAcknowledge(grnId: String, reason: String, userId: String) {
        grnRepository.rejectAcknowledge(grnId, reason, userId)
    }

    override fun approveAcknowledge(data: GRNAckDTO, userId: String) {
        try {
            if (data.costCenterCode !== null && data.category == ItemCategoryEnum.SAMPLES.id) {
                grnRepository.approveAcknowledge(data.category!!, data.expiryDate!!, userId, data.grnId!!,data.medicalCode!!,data.hsnCode!!,data.ratePer!!)
                val grn = grnRepository.getAcknowledgeDataById(data.grnId!!)
                var itemid: String? = null
                var i = 0
                if (grn.limid == null) {
                    var item = itemService.getItemDataByCode(data.itemCode!!)
                    if (item.isEmpty()) {
                        var addItemParam = Item()
                        addItemParam.id = UUID.randomUUID().toString()
                        addItemParam.name = grn.itemName
                        addItemParam.ciName = grn.itemName
                        addItemParam.code = data.itemCode
                        addItemParam.category = NamedSquerEntity(data.category!!, "")
                        addItemParam.description = grn.itemName
                        addItemParam.medicalCode = data.medicalCode
                        addItemParam.ccmId = NamedSquerEntity(grn.ccmId?.id.toString(), "")
                        addItemParam.active = 1
                        addItemParam.hsnCode = null
                        addItemParam.createdBy = userId
                        addItemParam.updatedBy = userId
                        itemService.insertItem(addItemParam)
                        item = itemService.getItemDataByCode(data.itemCode!!)
                    }
                    itemid = item.get(i).id
                } else {
                    val sample = sampleMasterService.getSampleByLmid(grn.limid!!)
                    itemid = sample.id
                }

                var vendorid: String? = null
                var vendor = vendorService.getVendorByCode(grn.vendorCode!!)
                 i = 0
                if (vendor.isEmpty()) {
                    var addVendorParam = Vendor()
                    addVendorParam.id = UUID.randomUUID().toString()
                    addVendorParam.name = grn.vendorName
                    addVendorParam.ciName = grn.vendorName
                    addVendorParam.code = grn.vendorCode
                    addVendorParam.active = 1
                    addVendorParam.createdBy = userId
                    addVendorParam.updatedBy = userId
                    vendorService.insertVendor(addVendorParam)
                    vendor = vendorService.getVendorByCode(grn.vendorCode!!)
                }
                vendorid = vendor.get(i).id

                var addInventoryParam = Inventory()
                addInventoryParam.id = UUID.randomUUID().toString()
                addInventoryParam.item = NamedSquerEntity(itemid.toString(),"")
                addInventoryParam.grnId = SquerEntity(grn.id.toString())
                addInventoryParam.packSize = data.basePack
                addInventoryParam.poNo = grn.poNo
//                addInventoryParam.ccmID = NamedSquerEntity(grn.ccmId?.id.toString(), "")
                addInventoryParam.ccmID = grn.ccmId?.let { NamedSquerEntity(it.id,"") }
                addInventoryParam.limid = grn.limid
                addInventoryParam.postingDate = grn.postingDate
                addInventoryParam.expiryDate = grn.expiryDate
                addInventoryParam.categoryId = NamedSquerEntity(data.category!!.toString(), "")
                addInventoryParam.medicalCode = data.medicalCode
                addInventoryParam.vendorId = NamedSquerEntity(vendorid.toString(), "")
                addInventoryParam.ratePerUnit = grn.ratePerUnit
                addInventoryParam.qtyReceived = grn.qty
                addInventoryParam.qtyAllocated = 0
                addInventoryParam.qtyDispatched = 0
                addInventoryParam.numBoxes = data.numBoxes?.toDouble()
                addInventoryParam.isUnitAllocation = null
                addInventoryParam.batchNo = grn.batchNo
                addInventoryParam.hsnCode = grn.hsnCode
                addInventoryParam.rate = grn.ratePerGRN
                addInventoryParam.units = data.units.toString()
                addInventoryParam.createdBy = userId
                addInventoryParam.updatedBy = userId
                inventoryService.insertInventory(addInventoryParam)
            } else {
                grnRepository.approveAcknowledge(data.category!!, data.expiryDate!!, userId, data.grnId!!, data.medicalCode!!, data.hsnCode!!, data.ratePer!!)
                val grn = grnRepository.getAcknowledgeDataById(data.grnId!!)
                var itemid: String? = null
                var i = 0
                if (grn.limid == null) {

                    var item = itemService.getItemDataByCode(data.itemCode!!)
                    if (item.isEmpty()) {
                        var addItemParam = Item()
                        addItemParam.id = UUID.randomUUID().toString()
                        addItemParam.name = grn.itemName
                        addItemParam.ciName = grn.itemName
                        addItemParam.code = data.itemCode
                        addItemParam.category = NamedSquerEntity(data.category!!, "")
                        addItemParam.description = grn.itemName
                        addItemParam.medicalCode = data.medicalCode
                        addItemParam.ccmId = NamedSquerEntity(grn.ccmId?.id!!, "")
                        addItemParam.active = 1
                        addItemParam.hsnCode = grn.hsnCode
                        addItemParam.createdBy = userId
                        addItemParam.updatedBy = userId
                        itemService.insertItem(addItemParam)
                        item = itemService.getItemDataByCode(data.itemCode!!)
                    }
                    itemid = item.get(i).id
                } else {
                    val sample = sampleMasterService.getSampleByLmid(grn.limid!!)
                    itemid = sample.id
                }

                var vendorid: String? = null
                var vendor = vendorService.getVendorByCode(grn.vendorCode!!)
                i = 0
                if (vendor.isEmpty()) {
                    var addVendorParam = Vendor()
                    addVendorParam.id = UUID.randomUUID().toString()
                    addVendorParam.name = grn.vendorName
                    addVendorParam.ciName = grn.vendorName
                    addVendorParam.code = grn.vendorCode
                    addVendorParam.active = 1
                    addVendorParam.createdBy = userId
                    addVendorParam.updatedBy = userId
                    vendorService.insertVendor(addVendorParam)
                    vendor = vendorService.getVendorByCode(grn.vendorCode!!)
                }
                vendorid = vendor.get(i).id

                var addInventoryParam = Inventory()
                addInventoryParam.id = UUID.randomUUID().toString()
                addInventoryParam.item = NamedSquerEntity(itemid,"")
                addInventoryParam.grnId = SquerEntity(grn.id)
                addInventoryParam.packSize = data.basePack
                addInventoryParam.poNo = grn.poNo
                addInventoryParam.ccmID = NamedSquerEntity(grn.ccmId?.id.toString(), "")
                addInventoryParam.limid = grn.limid
                addInventoryParam.postingDate = grn.postingDate
                addInventoryParam.expiryDate = grn.expiryDate
                addInventoryParam.categoryId = NamedSquerEntity(data.category!!, "")
                addInventoryParam.medicalCode = data.medicalCode
                addInventoryParam.vendorId = NamedSquerEntity(vendorid, "")
                addInventoryParam.ratePerUnit = grn.ratePerUnit
                addInventoryParam.qtyReceived = grn.qty
                addInventoryParam.qtyAllocated = 0
                addInventoryParam.qtyDispatched = 0
                addInventoryParam.numBoxes = data.numBoxes?.toDouble()
                addInventoryParam.isUnitAllocation = null
                addInventoryParam.batchNo = grn.batchNo
                addInventoryParam.hsnCode = grn.hsnCode
                addInventoryParam.rate = grn.ratePerGRN
                addInventoryParam.units = data.units.toString()
                addInventoryParam.createdBy = userId
                addInventoryParam.updatedBy = userId
                inventoryService.insertInventory(addInventoryParam)
                println("hello")
            }
        }catch(e: Exception){
            log.error("")
        }
    }

}
