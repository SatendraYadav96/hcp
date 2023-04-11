package com.squer.promobee.controller




import com.squer.promobee.controller.dto.InventoryDTO
import com.squer.promobee.controller.dto.InventoryReversalDTO
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.InventoryService

import com.squer.promobee.service.repository.domain.*
import liquibase.pro.packaged.bu
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import lombok.extern.slf4j.Slf4j
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@Slf4j
open class InventoryController@Autowired constructor(
    private val inventoryService: InventoryService
){

    @PutMapping("/editUnitAllocation")
    open fun editUnitAllocation(@RequestBody inv: InventoryDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.editUnitAllocation(inv)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PutMapping("/blockItem")
    open fun blockItem(@RequestBody inv: InventoryDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.blockItem(inv)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/searchInventory/{isExhausted}/{isPopup}")
    fun searchInventory ( @PathVariable isExhausted: Boolean, @PathVariable isPopup:Int): ResponseEntity<*> {
        val data = inventoryService.searchInventory( isExhausted, isPopup)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getInventoryReversalHistory/{invId}")
    fun getInventoryReversalHistory ( @PathVariable invId: String): ResponseEntity<*> {
        val data = inventoryService.getInventoryReversalHistory( invId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/reverseInventory")
    open fun reverseInventory(@RequestBody inv: InventoryReversalDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val reverseData = inventoryService.reverseInventory(inv)
        return ResponseEntity(reverseData, HttpStatus.OK)
    }





}