package com.squer.promobee.controller


import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.ApprovalService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@Slf4j
open class ApprovalController@Autowired constructor(
    private val approvalService: ApprovalService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    // MONTHLY APPROVAL

    @GetMapping("/getMonthlyApprovalForBex/{month}/{year}/{userId}/{userDesgId}")
    fun getMonthlyApprovalForBex(@PathVariable month : Int ,@PathVariable year : Int ,@PathVariable userId : String ,@PathVariable userDesgId : String ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getMonthlyApprovalForBex(month,year,userId,userDesgId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDispatchPlanById/{id}")
    fun getDispatchPlanById(@PathVariable id : String  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getDispatchPlanById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getMonthlyApprovalDetails/{userId}/{planId}")
    fun getMonthlyApprovalDetails(@PathVariable userId : String ,@PathVariable planId : String  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getMonthlyApprovalDetails(userId,planId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PutMapping("/unlockPlanForUserByMonthAndYear")
    fun unlockPlanForUserByMonthAndYear(@RequestBody plan : UnlockPlanDto  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.unlockPlanForUserByMonthAndYear(plan)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/resetDraftPlan/{planId}")
    fun resetDraftPlan(@PathVariable planId : String  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.resetDraftPlan(planId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getApprovalChainById/{id}")
    fun getApprovalChainById(@PathVariable id : String  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getApprovalChainById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getApprovalChainForSpecialPlanConvert/{id}")
    fun getApprovalChainForSpecialPlanConvert(@PathVariable id : String  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getApprovalChainForSpecialPlanConvert(id)
        return ResponseEntity(data, HttpStatus.OK)
    }



    @PutMapping("/approvePlan")
    fun approvePlan(@RequestBody plan : ApproveRejectPlanDto): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.approvePlan(plan)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDispatchDetails/{planId}")
    fun getDispatchDetails(@PathVariable planId : String  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getDispatchDetails(planId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getDispatchPlanCount/{id}")
    fun getDispatchPlanCount(@PathVariable id : String  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getDispatchPlanCount(id)
        return ResponseEntity(data, HttpStatus.OK)
    }




    @PutMapping("/rejectPlan")
    fun rejectPlan(@RequestBody plan : ApproveRejectPlanDto): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.rejectPlan(plan)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PutMapping("/saveMonthlyToSpecial")
    fun saveMonthlyToSpecial(@RequestBody plan : SaveMonthlyToSpecialDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.saveMonthlyToSpecial(plan)
        return ResponseEntity(data, HttpStatus.OK)
    }


    //SPECIAL APPROVAL

    @GetMapping("/getSpecialPlanForApproval/{month}/{year}/{userId}/{userDesgId}")
    fun getSpecialPlanForApproval(@PathVariable month : Int ,@PathVariable year : Int ,@PathVariable userId : String ,@PathVariable userDesgId : String ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getSpecialPlanForApproval(month,year,userId,userDesgId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getSpecialPlanApprovalDetails/{planId}")
    fun getSpecialPlanApprovalDetails(@PathVariable planId : String  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getSpecialPlanApprovalDetails(planId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    // VIRTUAL APPROVAL


    @GetMapping("/getVirtualPlanForApproval/{month}/{year}/{userId}/{userDesgId}")
    fun getVirtualPlanForApproval(@PathVariable month : Int ,@PathVariable year : Int ,@PathVariable userId : String ,@PathVariable userDesgId : String ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getVirtualPlanForApproval(month,year,userId,userDesgId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getVirtualPlanApprovalDetails/{planId}/{teamId}")
    fun getVirtualPlanApprovalDetails(@PathVariable planId : String , @PathVariable teamId : String ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.getVirtualPlanApprovalDetails(planId,teamId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/virtualAllocationDownload")
    fun virtualAllocationDownload(@RequestBody vrl : List<VirtualAllocationDownloadDTO> ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = approvalService.virtualAllocationDownload(vrl)
        return ResponseEntity(data, HttpStatus.OK)
    }









}