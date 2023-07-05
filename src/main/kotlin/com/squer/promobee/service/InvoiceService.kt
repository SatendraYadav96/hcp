package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.*


interface InvoiceService {


    fun getInvoiceHeaderById(id:String): InvoiceHeader

    fun getDoctorById(id:String): Doctor

    fun getPrintInvoiceHeaders(inhId:String): MutableList<InvoicePrintDetailsDTO>

    fun getVirtualPrintInvoiceHeaders(inhId:String): InvoicePrintDetailsDTO

    fun getInvoiceDetailsForPrint(inhId:String): MutableList<InvoiceDetailsPrintDTO>

    fun printInvoice(inh: List<PrintInvoiceDTO>): MutableList<ByteArray>?

    fun getHsnRate(hcmCode:String): HSN

    fun searchInvoice(searchInvoice: SearchInvoiceDTO): List<InvoiceHeaderDTO>

    fun getGroupInvoiceListHub(groupInvoice: GroupInvoiceParamDTO): List<GroupInvoicesListHubDTO>

    fun getInvoicesForGrouping(groupInvoice: GroupInvoiceParamDTO): List<InvoicesForGroupingDTO>


    fun printLabel(inh: List<PrintInvoiceDTO>): MutableList<ByteArray>?

    fun getRecipientToGenerateInvoice( recipientId: String):Recipient

    fun getInventoryByIdForInvoicing( invId: String):MutableList<Inventory>

    fun getRecipientItemCategoryCount( month:Int,year:Int,recipientId:String):MutableList<ItemCategoryCountDTO>

    fun getDispatchDetailsForInvoicing( month:Int,year:Int,recipientId:String):MutableList<DispatchDetailDTO>

    fun getItemMasterById(id: String): Item

    fun getSampleMasterById(id: String): SampleMaster

    fun getDispatchPlanById(id: String): DispatchPlan

    fun generateInvoice(genInv : List<GenerateInvoiceDTO>)

    fun getInvoice(invoiceNo: Int): InvoiceHeader

    fun getTransporter(name: String): Transporter

    fun getDocket(docketName: String): DocketDTO




}