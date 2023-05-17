package com.squer.promobee.controller


import com.squer.promobee.controller.dto.GenerateInvoiceDTO
import com.squer.promobee.controller.dto.GroupInvoiceParamDTO
import com.squer.promobee.controller.dto.PrintInvoiceDTO
import com.squer.promobee.controller.dto.SearchInvoiceDTO
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.InvoiceService
import lombok.extern.slf4j.Slf4j
import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@Slf4j
open class InvoiceController@Autowired constructor(
    private val invoiceService: InvoiceService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/getInvoiceHeaderById/{id}")
    open fun getInvoiceHeaderById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val inhData = invoiceService.getInvoiceHeaderById(id)
        return ResponseEntity(inhData, HttpStatus.OK)
    }

    @GetMapping("/getDoctorById/{id}")
    open fun getDoctorById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val doc = invoiceService.getDoctorById(id)
        return ResponseEntity(doc, HttpStatus.OK)
    }

    @GetMapping("/getPrintInvoiceHeaders/{inhId}")
    open fun getPrintInvoiceHeaders(@PathVariable inhId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printDetails = invoiceService.getPrintInvoiceHeaders(inhId)
        return ResponseEntity(printDetails, HttpStatus.OK)
    }

    @GetMapping("/getVirtualPrintInvoiceHeaders/{inhId}")
    open fun getVirtualPrintInvoiceHeaders(@PathVariable inhId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printDetailsVirtual = invoiceService.getVirtualPrintInvoiceHeaders(inhId)
        return ResponseEntity(printDetailsVirtual, HttpStatus.OK)
    }

    @GetMapping("/getInvoiceDetailsForPrint/{inhId}")
    open fun getInvoiceDetailsForPrint(@PathVariable inhId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val invoiceDetails = invoiceService.getInvoiceDetailsForPrint(inhId)
        return ResponseEntity(invoiceDetails, HttpStatus.OK)
    }


    @PostMapping("/printInvoice")
    open fun printInvoice(@RequestBody inh: PrintInvoiceDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printInvoiceData = invoiceService.printInvoice(inh)

        return ResponseEntity(printInvoiceData, HttpStatus.OK)
    }

    @GetMapping("/getHsnRate/{hcmCode}")
    open fun getHsnRate(@PathVariable hcmCode: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val hsnData = invoiceService.getHsnRate(hcmCode)
        return ResponseEntity(hsnData, HttpStatus.OK)
    }

    @PostMapping("/searchInvoice")
    open fun searchInvoice(@RequestBody searchInvoice: SearchInvoiceDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val searchInvoiceData = invoiceService.searchInvoice(searchInvoice)

        return ResponseEntity(searchInvoiceData, HttpStatus.OK)
    }

    @PostMapping("/getGroupInvoiceListHub")
    open fun getGroupInvoiceListHub(@RequestBody groupInvoice: GroupInvoiceParamDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val groupInvoiceData = invoiceService.getGroupInvoiceListHub(groupInvoice)

        return ResponseEntity(groupInvoiceData, HttpStatus.OK)
    }


    @PostMapping("/getInvoicesForGrouping")
    open fun getInvoicesForGrouping(@RequestBody groupInvoice: GroupInvoiceParamDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val groupInvoicingData = invoiceService.getInvoicesForGrouping(groupInvoice)

        return ResponseEntity(groupInvoicingData, HttpStatus.OK)
    }


    @PostMapping("/printLabel")
    open fun printLabel(@RequestBody inh: PrintInvoiceDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printLabelData = invoiceService.printLabel(inh)

        return ResponseEntity(printLabelData, HttpStatus.OK)
    }


    @GetMapping("/generateDraftedInvoice/{month}/{year}/{recipientId}")
        open fun generateDraftedInvoice(@PathVariable month: Int, @PathVariable year: Int,@PathVariable recipientId: String ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val generateInvoiceData = invoiceService.generateDraftedInvoice(month,year,recipientId)

        return ResponseEntity(generateInvoiceData, HttpStatus.OK)
    }







}