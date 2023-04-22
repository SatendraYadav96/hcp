package com.squer.promobee.controller


import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.InvoiceService
import lombok.extern.slf4j.Slf4j
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


    @GetMapping("/printInvoice")
    open fun printInvoice(@RequestBody inh: InvoiceHeaderDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printInvoiceData = invoiceService.printInvoice(inh)
        return ResponseEntity(printInvoiceData, HttpStatus.OK)
    }





}