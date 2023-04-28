package com.squer.promobee.service

import com.squer.promobee.controller.dto.InvoiceDetailsPrintDTO
import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.controller.dto.InvoicePrintDetailsDTO
import com.squer.promobee.controller.dto.PrintInvoiceDTO
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





}