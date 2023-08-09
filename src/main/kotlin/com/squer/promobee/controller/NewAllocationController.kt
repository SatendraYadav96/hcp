package com.squer.promobee.controller

import com.squer.promobee.service.NewAllocationService
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
open class NewAllocationController @Autowired constructor(
    private val newAllocationService: NewAllocationService
){


    @GetMapping("/getTseList/{id}")
    open fun getTseList(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.getTseList(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/assignTse/{id}")
    open fun assignTse(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.assignTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/unAssignTse/{id}")
    open fun unAssignTse(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.unAssignTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getBrandManagerForTse/{id}")
    open fun getBrandManagerForTse(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.getBrandManagerForTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    //SPECIAL ALLOCATION

    @GetMapping("/getTeamForSpeicalAllocation/{id}")
    open fun getTeamForSpeicalAllocation(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.getBrandManagerForTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    //MONTHLY ALLOCATION

    @PostMapping("/monthly/create")
    open fun createMonthlyPlan(@RequestBody yearMonth: Map<String , Long>): ResponseEntity<*>{
        val items = newAllocationService.createMonthlyPlan(yearMonth["yearMonth"]!!)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @GetMapping("/isPlanApprovedOrSubmitLock/{month}/{year}")
    open fun isPlanApprovedOrSubmitLock(@PathVariable month: String , @PathVariable year: String): ResponseEntity<*> {
        val data = newAllocationService.isPlanApprovedOrSubmitLock(month , year)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getcheckforsampleFifocheckpopup/{planId}/{inventoryId}/{isItem}")
    open fun getcheckforsampleFifocheckpopup(@PathVariable planId: String , @PathVariable inventoryId: String, @PathVariable isItem: Int): ResponseEntity<*> {
        val data = newAllocationService.getcheckforsampleFifocheckpopup(planId , inventoryId ,isItem )
        return ResponseEntity(data, HttpStatus.OK)
    }











}