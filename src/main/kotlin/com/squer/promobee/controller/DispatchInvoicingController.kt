package com.squer.promobee.controller

import com.squer.promobee.service.DispatchInvoicingService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Slf4j
open class DispatchInvoicingController @Autowired constructor(
        private val dispatchInvoicingService: DispatchInvoicingService,




) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/getPickingList/{year}/{month}/{dispatchType}")
    fun getPickingList(
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable dispatchType: String
    ): ResponseEntity<*> {
        val data = dispatchInvoicingService.getPickingList(year, month, dispatchType)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getMonthlyDispatchSearch/{year}/{month}")
    fun getMonthlyDispatchSearch(@PathVariable year: Int, @PathVariable month: Int): ResponseEntity<*> {
        val data = dispatchInvoicingService.getMonthlyDispatchSearch(month, year)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getEmployeeInvoiceDetails/{year}/{month}/{isSpecialDisp}/{teamId}/{status}")
    fun getEmployeeInvoiceDetails(
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable isSpecialDisp: String,
        @PathVariable teamId: String,
        @PathVariable status: String
    ): ResponseEntity<*> {
        val data = dispatchInvoicingService.getEmployeeInvoiceDetails(month, year, isSpecialDisp, teamId, status)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getGroupInvoiceSearch/{fromDate}/{toDate}/{invoiceNumber}/{type}")
    fun getGroupingInvoiceForHUB(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") fromDate: Date,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") toDate: Date,
        @PathVariable invoiceNumber: Int,
        @PathVariable type: String
    ): ResponseEntity<*> {
        val data = dispatchInvoicingService.getGroupingInvoiceForHUB(fromDate, toDate, invoiceNumber, type)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getSpecialDispatchSearch/{year}/{month}")
    fun getSpecialDispatchSearch(@PathVariable year: Int, @PathVariable month: Int): ResponseEntity<*> {
        val data = dispatchInvoicingService.getSpecialDispatchSearch(year, month)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getSpecialEmployeeInvoiceDetails/{planId}/{status}")
    fun getSpecialEmployeeInvoiceDetails(@PathVariable planId: String, @PathVariable status: String): ResponseEntity<*> {
        val data = dispatchInvoicingService.getSpecialEmployeeInvoiceDetails(planId, status)
        return ResponseEntity(data, HttpStatus.OK)

    }

    @GetMapping("/getVirtualDispatchSearch/{year}/{month}")
    fun getVirtualDispatchSearch(@PathVariable year: Int, @PathVariable month: Int): ResponseEntity<*> {
        val data = dispatchInvoicingService.getVirtualDispatchSearch(month, year)
        return ResponseEntity(data, HttpStatus.OK)
    }







}

