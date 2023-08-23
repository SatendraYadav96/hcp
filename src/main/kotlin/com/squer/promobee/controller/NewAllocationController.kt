package com.squer.promobee.controller

import com.squer.promobee.controller.dto.CreateAllocationDTO
import com.squer.promobee.controller.dto.saveCommonAllocationDTO
import com.squer.promobee.controller.dto.saveDifferentialAllocation
import com.squer.promobee.controller.dto.submitAllocationDTO
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

    @GetMapping("/searchSpecialPlan/{planId}")
    open fun searchSpecialPlan(@PathVariable month: Int, @PathVariable year: Int,@PathVariable status: String,@PathVariable remark: String): ResponseEntity<*> {
        val data = newAllocationService.searchSpecialPlan(month,year,status,remark )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getTeamForSpecialAllocation/{ccmId}")
    open fun getTeamForSpecialAllocation(@PathVariable ccmId: String): ResponseEntity<*> {
        val data = newAllocationService.getTeamForSpecialAllocation(ccmId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getRecipientForSpecialAllocation/{teamId}")
    open fun getRecipientForSpecialAllocation(@PathVariable teamId: String): ResponseEntity<*> {
        val data = newAllocationService.getRecipientForSpecialAllocation(teamId)
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


    //VIRTUAL ALLOCATION


    @PostMapping("/virtual/create")
    open fun createVirtualPlan(@RequestBody yearMonth: Map<String , Long>): ResponseEntity<*>{
        val items = newAllocationService.createVirtualPlan(yearMonth["yearMonth"]!!)
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







































}