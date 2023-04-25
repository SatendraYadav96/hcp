package com.squer.promobee.service

import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.controller.dto.PrintInvoiceDTO
import com.squer.promobee.service.repository.domain.HSN
import com.squer.promobee.service.repository.domain.InvoiceHeader


interface InvoiceService {


    fun getInvoiceHeaderById(id:String): InvoiceHeader

    fun printInvoice(inh:PrintInvoiceDTO)

    fun getHsnRate(hcmCode:String): HSN



}