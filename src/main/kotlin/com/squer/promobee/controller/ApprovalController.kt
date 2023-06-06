package com.squer.promobee.controller


import com.squer.promobee.controller.dto.FieldForceDTO
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




}