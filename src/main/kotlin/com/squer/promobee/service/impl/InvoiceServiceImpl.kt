package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.InvoiceService
import com.squer.promobee.service.repository.InvoiceRepository
import com.squer.promobee.service.repository.domain.Doctor
import com.squer.promobee.service.repository.domain.HSN
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

    override fun getDoctorById(id:String): Doctor {
        return invoiceRepository.getDoctorById(id)
    }

    override fun getPrintInvoiceHeaders(inhId:String): InvoicePrintDetailsDTO {
        return invoiceRepository.getPrintInvoiceHeaders(inhId)
    }

    override fun getVirtualPrintInvoiceHeaders(inhId:String): InvoicePrintDetailsDTO {
        return invoiceRepository.getVirtualPrintInvoiceHeaders(inhId)
    }

    override fun getInvoiceDetailsForPrint(inhId:String): List<InvoiceDetailsPrintDTO> {
        return invoiceRepository.getInvoiceDetailsForPrint(inhId)
    }


    override fun printInvoice(inh: PrintInvoiceDTO) {
         invoiceRepository.printInvoice(inh)
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



}