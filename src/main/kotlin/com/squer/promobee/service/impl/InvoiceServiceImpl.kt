package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.service.InvoiceService
import com.squer.promobee.service.repository.InvoiceRepository
import com.squer.promobee.service.repository.domain.InvoiceHeader

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class InvoiceServiceImpl @Autowired constructor(
    private val invoiceRepository: InvoiceRepository


): InvoiceService {


    private val log = LoggerFactory.getLogger(javaClass)


    override fun getInvoiceHeaderById(id:String): InvoiceHeader {
        return invoiceRepository.getInvoiceHeaderById(id)
    }


    override fun printInvoice(inh: InvoiceHeaderDTO) {
         invoiceRepository.printInvoice(inh)
    }



}