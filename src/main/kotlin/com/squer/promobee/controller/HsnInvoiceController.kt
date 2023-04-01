package com.squer.promobee.controller

import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.HsnInvoiceService
import com.squer.promobee.service.ReportService
import com.squer.promobee.service.repository.domain.HSN
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@Slf4j
open class HsnInvoiceController@Autowired constructor(
    private val hsnInvoiceService: HsnInvoiceService
) {

    private val log = LoggerFactory.getLogger(javaClass)


/*    @PostMapping("/addHsn")
    open fun addHsn(@RequestBody data: Map<String, String>): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        data?.get("rate")?.let { hsnInvoiceService.addHsn(data?.get("hcmCode").toString(), it.toString(), user.id) }
        return ResponseEntity(null, HttpStatus.OK)
    }*/

    @PostMapping("/addHsn")
    open fun addHsn(@RequestBody hsn:HSN): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val insertHsn = hsnInvoiceService.addHsn(hsn)
        return ResponseEntity(insertHsn, HttpStatus.OK)
    }

    @PutMapping("/editInvoiceHeader")
    open fun editInvoiceHeader(@RequestBody inh: InvoiceHeaderDTO):ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val editInh = hsnInvoiceService.editInvoiceHeader(inh)
        return ResponseEntity(editInh , HttpStatus.OK )

    }



}