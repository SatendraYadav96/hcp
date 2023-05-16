package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.Doctor
import com.squer.promobee.service.repository.domain.HSN
import com.squer.promobee.service.repository.domain.InvoiceHeader


interface InvoiceService {


    fun getInvoiceHeaderById(id:String): InvoiceHeader

    fun getDoctorById(id:String): Doctor

    fun getPrintInvoiceHeaders(inhId:String): InvoicePrintDetailsDTO

    fun getVirtualPrintInvoiceHeaders(inhId:String): InvoicePrintDetailsDTO

    fun getInvoiceDetailsForPrint(inhId:String): List<InvoiceDetailsPrintDTO>

    fun printInvoice(inh:PrintInvoiceDTO)

    fun getHsnRate(hcmCode:String): HSN

    fun searchInvoice(searchInvoice: SearchInvoiceDTO): List<InvoiceHeaderDTO>

    fun getGroupInvoiceListHub(groupInvoice: GroupInvoiceParamDTO): List<GroupInvoicesListHubDTO>

    fun getInvoicesForGrouping(groupInvoice: GroupInvoiceParamDTO): List<InvoicesForGroupingDTO>


    fun printLabel(inh:PrintInvoiceDTO)






}