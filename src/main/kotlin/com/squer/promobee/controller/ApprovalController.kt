package com.squer.promobee.controller


import com.squer.promobee.controller.dto.ApproveRejectPlanDto
import com.squer.promobee.controller.dto.FieldForceDTO
import com.squer.promobee.controller.dto.SaveMonthlyToSpecialDTO
import com.squer.promobee.controller.dto.UnlockPlanDto
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.ApprovalService
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
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody


@Slf4j
open class ApprovalController@Autowired constructor(
    private val approvalService: ApprovalService
) {

    private val log = LoggerFactory.getLogger(javaClass)

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







}