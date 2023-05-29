package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.*
import java.io.ByteArrayOutputStream
import java.io.Serializable


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

    fun getRecipientItemCategoryCount( month:Int,year:Int,recipientId:String):ItemCategoryCountDTO

    fun getDispatchDetailsForInvoicing( month:Int,year:Int,recipientId:String):List<DispatchDetailDTO>

    fun getItemMasterById(id: String): Item

    fun getSampleMasterById(id: String): SampleMaster

    fun getDispatchPlanById(id: String): DispatchPlan

    fun generateInvoice(genInv : GenerateInvoiceDTO)




}