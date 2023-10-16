package com.squer.promobee.controller

import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.NewAllocationService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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

    @PostMapping("/assignTse/{id}")
    open fun assignTse(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.assignTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/unAssignTse/{id}")
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

    @GetMapping("/monthly/create/{year}/{month}")
    open fun createMonthlyPlan(@PathVariable year: Int , @PathVariable month: Int): ResponseEntity<*>{
        val items = newAllocationService.createMonthlyPlan(year,month)
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

    @GetMapping("/getTeamForCommonAllocation/{ccmId}")
    open fun getTeamForCommonAllocation(@PathVariable ccmId :String ): ResponseEntity<*> {
        val data = newAllocationService.getTeamForCommonAllocation(ccmId )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getQuantityAllocatedOfUserToItem/{userId}/{inventoryId}/{month}/{year}/{isSpecialDispatch}")
    open fun getQuantityAllocatedOfUserToItem(@PathVariable userId :String,@PathVariable inventoryId :String,@PathVariable month :Int,
        @PathVariable year :Int,@PathVariable isSpecialDispatch :Int): ResponseEntity<*> {
        val data = newAllocationService.getQuantityAllocatedOfUserToItem(userId,inventoryId,month,year,isSpecialDispatch )
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getQuantityAllocatedDifferentialRecipient/{planId}/{inventoryId}/{teamId}")
    open fun getQuantityAllocatedDifferentialRecipient(@PathVariable planId :String,@PathVariable inventoryId :String,@PathVariable teamId :String): ResponseEntity<*> {
        val data = newAllocationService.getQuantityAllocatedDifferentialRecipient(planId,inventoryId,teamId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getTeamForDifferentialAllocation/{planId}/{teamId}/{inventoryId}")
    open fun getTeamForDifferentialAllocation(@PathVariable planId :String , @PathVariable teamId :String , @PathVariable inventoryId :String ): ResponseEntity<*> {
        val data = newAllocationService.getTeamForDifferentialAllocation(planId, teamId , inventoryId )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/saveCommonAllocation")
    open fun saveCommonAllocation(@RequestBody saveAlloc : List<saveCommonAllocationDTO>): ResponseEntity<*>{
        val items = newAllocationService.saveCommonAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @PostMapping("/saveDifferentialAllocation")
    open fun saveDifferentialAllocation(@RequestBody saveAlloc : List<saveDifferentialAllocation>): ResponseEntity<*>{
        val items = newAllocationService.saveDifferentialAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @PostMapping("/submitMonthlyAllocation")
    open fun submitMonthlyAllocation(@RequestBody alloc : submitAllocationDTO): ResponseEntity<*>{
        val items = newAllocationService.submitMonthlyAllocation(alloc)
        return ResponseEntity(items, HttpStatus.OK)
    }


    //SPECIAL ALLOCATION


    @PostMapping("/special/create")
    open fun createSpecialPlan(@RequestBody alloc : CreateAllocationDTO): ResponseEntity<*>{
        val items = newAllocationService.createSpecialPlan(alloc)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @GetMapping("/special/edit/{planId}")
    open fun editSpecialPlan(@PathVariable planId: String): ResponseEntity<*> {
        val data = newAllocationService.editSpecialPlan(planId )
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/searchSpecialPlan/{month}/{year}/{status}/{remark}")
    open fun searchSpecialPlan(@PathVariable month: Int, @PathVariable year: Int,@PathVariable status: String,@PathVariable remark: String): ResponseEntity<*> {
        val data = newAllocationService.searchSpecialPlan(month,year,status,remark )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getTeamForSpecialAllocation/{ccmId}")
    open fun getTeamForSpecialAllocation(@PathVariable ccmId: String): ResponseEntity<*> {
        val data = newAllocationService.getTeamForSpecialAllocation(ccmId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getRecipientForSpecialAllocation/{ccmId}")
    open fun getRecipientForSpecialAllocation(@PathVariable ccmId: String): ResponseEntity<*> {
        val data = newAllocationService.getRecipientForSpecialAllocation(ccmId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getSpecialQuantityAllocatedDifferentialRecipient/{planId}/{inventoryId}/{teamId}")
    open fun getSpecialQuantityAllocatedDifferentialRecipient(@PathVariable planId :String,@PathVariable inventoryId :String,@PathVariable teamId :String): ResponseEntity<*> {
        val data = newAllocationService.getSpecialQuantityAllocatedDifferentialRecipient(planId,inventoryId,teamId)
        return ResponseEntity(data, HttpStatus.OK)
    }




    @PostMapping("/saveSpecialAllocation")
    open fun saveSpecialAllocation(@RequestBody saveAlloc : List<saveDifferentialAllocation>): ResponseEntity<*>{
        val items = newAllocationService.saveSpecialAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @PostMapping("/deleteSpecialAllocation/{dipId}")
    open fun deleteSpecialAllocation(@PathVariable dipId: String): ResponseEntity<*> {
        val data = newAllocationService.deleteSpecialAllocation(dipId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/deleteSpecialAllocationDID/{dipId}")
    open fun deleteSpecialAllocationDID(@PathVariable dipId: String): ResponseEntity<*> {
        val data = newAllocationService.deleteSpecialAllocationDID(dipId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/submitSpecialAllocation")
    open fun submitSpecialAllocation(@RequestBody alloc : submitAllocationDTO): ResponseEntity<*>{
        val items = newAllocationService.submitSpecialAllocation(alloc)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @GetMapping("/getAllocationStatusDropdown")
    fun getAllocationStatusDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = newAllocationService.getAllocationStatusDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }





    //VIRTUAL ALLOCATION


    @GetMapping("/virtual/create/{year}/{month}")
    open fun createVirtualPlan(@PathVariable year: Int , @PathVariable month: Int): ResponseEntity<*>{
        val items = newAllocationService.createVirtualPlan(year,month)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @GetMapping("/isVirtualPlanApprovedOrSubmitLock/{month}/{year}")
    open fun isVirtualPlanApprovedOrSubmitLock(@PathVariable month: String , @PathVariable year: String): ResponseEntity<*> {
        val data = newAllocationService.isVirtualPlanApprovedOrSubmitLock(month , year)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getVirtualTeamForCommonAllocation/{ccmId}")
    open fun getVirtualTeamForCommonAllocation(@PathVariable ccmId :String ): ResponseEntity<*> {
        val data = newAllocationService.getVirtualTeamForCommonAllocation(ccmId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getVirtualQuantityAllocatedToUser/{userId}/{inventoryId}/{month}/{year}/{isSpecialDispatch}/{planId}")
    open fun getVirtualQuantityAllocatedToUser(@PathVariable userId :String,@PathVariable inventoryId :String,@PathVariable month :Int,
                                              @PathVariable year :Int,@PathVariable isSpecialDispatch :Int,@PathVariable planId :String): ResponseEntity<*> {
        val data = newAllocationService.getVirtualQuantityAllocatedToUser(userId,inventoryId,month,year,isSpecialDispatch, planId )
        return ResponseEntity(data, HttpStatus.OK)
    }



    @GetMapping("/getVirtualTeamForDifferentialAllocation/{planId}/{teamId}/{inventoryId}")
    open fun getVirtualTeamForDifferentialAllocation(@PathVariable planId :String , @PathVariable teamId :String , @PathVariable inventoryId :String ): ResponseEntity<*> {
        val data = newAllocationService.getVirtualTeamForDifferentialAllocation(planId, teamId , inventoryId )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getVirtualQuantityAllocatedDifferentialRecipient/{planId}/{inventoryId}/{teamId}")
    open fun getVirtualQuantityAllocatedDifferentialRecipient(@PathVariable planId :String,@PathVariable inventoryId :String,@PathVariable teamId :String): ResponseEntity<*> {
        val data = newAllocationService.getVirtualQuantityAllocatedDifferentialRecipient(planId,inventoryId,teamId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/saveVirtualCommonAllocation")
    open fun saveVirtualCommonAllocation(@RequestBody saveAlloc : List<saveVirtualCommonAllocationDTO>): ResponseEntity<*>{
        val items = newAllocationService.saveVirtualCommonAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @PostMapping("/saveVirtualDifferentialAllocation")
    open fun saveVirtualDifferentialAllocation(@RequestBody saveAlloc : List<saveDifferentialAllocation>): ResponseEntity<*>{
        val items = newAllocationService.saveVirtualDifferentialAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @PostMapping("/submitVirtualAllocation")
    open fun submitVirtualAllocation(@RequestBody alloc : submitAllocationDTO): ResponseEntity<*>{
        val items = newAllocationService.submitVirtualAllocation(alloc)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @GetMapping("/getActiveUsers/{userId}")
    open fun getActiveUsers(@PathVariable userId: String): ResponseEntity<*> {
        val data = newAllocationService.getActiveUsers(userId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDownloadAllocation/{planId}")
    open fun getDownloadAllocation(@PathVariable planId: String): ResponseEntity<*> {
        val data = newAllocationService.getDownloadAllocation(planId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getBlockedRecipients/{code}")
    open fun getBlockedRecipients(@PathVariable code: String): ResponseEntity<*> {
        val data = newAllocationService.getBlockedRecipients(code)
        return ResponseEntity(data, HttpStatus.OK)
    }




    @PostMapping("/getMultipleAllocation")
    open fun getMultipleAllocation(@RequestBody mulAlloc: List<MultipleAllocationExcelDTO>): ResponseEntity<*> {
        val data = newAllocationService.getMultipleAllocation(mulAlloc)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/getMultipleAllocationExcel")
    open fun getMultipleAllocationExcel(@RequestBody mulAlloc: List<MultipleAllocationExcelDTO>): ResponseEntity<*> {
        val data = newAllocationService.getMultipleAllocationExcel(mulAlloc)
        return ResponseEntity(data, HttpStatus.OK)
    }




























































}