package com.squer.promobee.controller




import com.squer.promobee.controller.dto.InventoryDTO
import com.squer.promobee.controller.dto.InventoryReversalDTO
import com.squer.promobee.controller.dto.SwitchInventoryDTO
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

    @PutMapping("/editUnitAllocation/{invId}")
    open fun editUnitAllocation(@PathVariable invId: String,@RequestBody inv: InventoryDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.editUnitAllocation(inv)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PutMapping("/blockItem/{invId}" )
    open fun blockItem(@PathVariable invId: String,@RequestBody inv: InventoryDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.blockItem(inv)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/searchInventory/{isExhausted}/{isPopup}")
    fun searchInventory ( @PathVariable isExhausted: Int, @PathVariable isPopup:Int): ResponseEntity<*> {
        val data = inventoryService.searchInventory( isExhausted, isPopup)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getInventoryReversalHistory/{invId}")
    fun getInventoryReversalHistory ( @PathVariable invId: String): ResponseEntity<*> {
        val data = inventoryService.getInventoryReversalHistory( invId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getInventoryReversalBMAllocation/{invId}")
    fun getInventoryReversalBMAllocation ( @PathVariable invId: String): ResponseEntity<*> {
        val data = inventoryService.getInventoryReversalBMAllocation( invId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getInventoryById/{invId}")
    fun getInventoryById ( @PathVariable invId: String): ResponseEntity<*> {
        val data = inventoryService.getInventoryById( invId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getMaxInvoiceNo")
    fun getMaxInvoiceNo ( ): ResponseEntity<*> {
        val data = inventoryService.getMaxInvoiceNo( )
        return ResponseEntity(data, HttpStatus.OK)
    }



    @PostMapping("/reverseInventory")
    open fun reverseInventory(@RequestBody inv: InventoryReversalDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val reverseData = inventoryService.reverseInventory(inv)
        return ResponseEntity(reverseData, HttpStatus.OK)
    }

    @PostMapping("/switchInventory")
    open fun switchInventory(@RequestBody inv: SwitchInventoryDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val switchData = inventoryService.switchInventory(inv)
        return ResponseEntity(switchData, HttpStatus.OK)
    }

    @GetMapping("/getPickList/{teamId}/{month}/{year}/{isSpecial}")
    fun getPickList( @PathVariable teamId: String , @PathVariable month: Int , @PathVariable year: Int, @PathVariable isSpecial: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.getPickList(teamId, month, year, isSpecial)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getPickListVirtual/{teamId}/{month}/{year}/{isSpecial}")
    fun getPickListVirtual( @PathVariable teamId: String , @PathVariable month: Int , @PathVariable year: Int, @PathVariable isSpecial: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.getPickListVirtual(teamId, month, year, isSpecial)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getPickListStatusByBM/{teamId}/{month}/{year}")
    fun getPickListStatusByBM( @PathVariable teamId: String , @PathVariable month: Int , @PathVariable year: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.getPickListStatusByBM(teamId, month, year)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getSpecialDispatchListForInvoicing/{planId}/{status}")
    fun getSpecialDispatchListForInvoicing( @PathVariable planId: String , @PathVariable status: String ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.getSpecialDispatchListForInvoicing(planId, status)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getVirtualDispatchListForInvoicing/{planId}/{status}")
    fun getVirtualDispatchListForInvoicing( @PathVariable planId: String , @PathVariable status: String ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.getVirtualDispatchListForInvoicing(planId, status)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getEmployeeInvoicePopupDetails/{month}/{year}/{isSpecial}/{employeeId}/{invoiceHeaderId}")
    fun getEmployeeInvoicePopupDetails( @PathVariable month: Int , @PathVariable year: Int , @PathVariable isSpecial: Int , @PathVariable employeeId: String , @PathVariable invoiceHeaderId: String ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = inventoryService.getEmployeeInvoicePopupDetails(month, year,isSpecial,employeeId,invoiceHeaderId)
        return ResponseEntity(data, HttpStatus.OK)
    }











}