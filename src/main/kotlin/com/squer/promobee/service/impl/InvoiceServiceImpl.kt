package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.InvoiceService
import com.squer.promobee.service.repository.InvoiceRepository
import com.squer.promobee.service.repository.domain.*
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@Slf4j
class InvoiceServiceImpl @Autowired constructor(
    private val invoiceRepository: InvoiceRepository


): InvoiceService {


    private val log = LoggerFactory.getLogger(javaClass)


    override fun getInvoiceHeaderById(id:String): InvoiceHeader {
        return invoiceRepository.getInvoiceHeaderById(id)
    }

    override fun getDoctorById(id:String): Doctor {
        return invoiceRepository.getDoctorById(id)
    }

    override fun getPrintInvoiceHeaders(inhId:String): MutableList<InvoicePrintDetailsDTO> {
        return invoiceRepository.getPrintInvoiceHeaders(inhId)
    }



    override fun getVirtualPrintInvoiceHeaders(inhId:String): InvoicePrintDetailsDTO {
        return invoiceRepository.getVirtualPrintInvoiceHeaders(inhId)
    }

    override fun getInvoiceDetailsForPrint(inhId:String): MutableList<InvoiceDetailsPrintDTO> {
        return invoiceRepository.getInvoiceDetailsForPrint(inhId)
    }


    override fun printInvoice(inh: List<PrintInvoiceDTO>) : MutableList<ByteArray>?{
         return invoiceRepository.printInvoice(inh)
    }
    override fun getHsnRate(hcmCode:String): HSN {
        return invoiceRepository.getHsnRate(hcmCode)
    }

    override fun searchInvoice(searchInvoice: SearchInvoiceDTO): List<InvoiceHeaderDTO> {
        return invoiceRepository.searchInvoice(searchInvoice)
    }

    override fun getGroupInvoiceListHub(groupInvoice: GroupInvoiceParamDTO): List<GroupInvoicesListHubDTO> {
        return invoiceRepository.getGroupInvoiceListHub(groupInvoice)
    }

    override fun getInvoicesForGrouping(groupInvoice: GroupInvoiceParamDTO): List<InvoicesForGroupingDTO> {
        return invoiceRepository.getInvoicesForGrouping(groupInvoice)
    }

    override fun printLabel(inh: List<PrintInvoiceDTO>): MutableList<ByteArray>? {
        return invoiceRepository.printLabel(inh)
    }

    override fun getRecipientToGenerateInvoice( recipientId: String):Recipient {
        return invoiceRepository.getRecipientToGenerateInvoice(recipientId)
    }

    override fun getDoctorToGenerateInvoice(id: String): Doctor {
        return  invoiceRepository.getDoctorToGenerateInvoice(id)
    }

    override fun getInventoryByIdForInvoicing(invId: String): MutableList<Inventory> {
        return invoiceRepository.getInventoryByIdForInvoicing(invId)
    }

    override fun getRecipientItemCategoryCount(month: Int, year: Int,  recipientId: String,isSpecial:Int,planId: String):ItemCategoryCountDTO {
        return invoiceRepository.getRecipientItemCategoryCount(month,year,recipientId,isSpecial,planId)
    }

    override fun getDoctorItemCategoryCount(planId: String, recipientId: String): MutableList<ItemCategoryCountDTO> {
        return invoiceRepository.getDoctorItemCategoryCount(planId,recipientId)
    }

    override fun getDispatchDetailsForInvoicing(month: Int, year: Int, recipientId: String,isSpecial:Int , planId: String): MutableList<DispatchDetailDTO> {
        return invoiceRepository.getDispatchDetailsForInvoicing(month,year,recipientId,isSpecial,planId)
    }

    override fun getDispatchDetailVirtual(planId: String, recipientId: String): MutableList<DispatchDetailDTO> {
        return invoiceRepository.getDispatchDetailVirtual(planId,recipientId)
    }

    override fun getItemMasterById(id: String): Item {
        return invoiceRepository.getItemMasterById(id)
    }

    override fun getSampleMasterById(id: String): SampleMaster {
        return invoiceRepository.getSampleMasterById(id)
    }

    override fun getDispatchPlanById(id: String): DispatchPlan {
        return invoiceRepository.getDispatchPlanById(id)
    }

    override fun generateInvoice(genInv : GenerateInvoiceDTO) {
        return invoiceRepository.generateInvoice(genInv)
    }

    override fun getInvoice(invoiceNo: Int): InvoiceHeader {
        return invoiceRepository.getInvoice(invoiceNo)
    }


    override fun getTransporter(name: String): Transporter {
        return invoiceRepository.getTransporter(name)
    }

    override fun getDocket(docketName: String): DocketDTO {
        return invoiceRepository.getDocket(docketName)
    }

    override fun generateInvoiceVirtual(genInv: GenerateInvoiceVirtualDTO) {
        return invoiceRepository.generateInvoiceVirtual(genInv)
    }

}