package com.squer.promobee.controller

import com.squer.promobee.service.AllocationService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@Slf4j
open class AllocationController @Autowired constructor(
        private val allocationService: AllocationService
){

    @PostMapping("/monthly/createview")
    open fun createViewMonthlyPlan(@RequestBody yearMonth: Long): ResponseEntity<*>{
        val items = allocationService.createViewMonthlyPlan(yearMonth)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @GetMapping("/allocationForPlan/{planId}")
    open fun allocationForPlan(@PathVariable planId: String): ResponseEntity<*> {
        val data = allocationService.allocationForPlan(planId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    /*@GetMapping("/monthly/distribution/{planId}/{itemId}")
    open fun getDistributionForPlan(@PathVariable planId: String, @PathVariable itemId: String){

    }*/




}