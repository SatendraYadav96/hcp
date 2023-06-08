package com.squer.promobee.controller




import com.squer.promobee.security.domain.User
import com.squer.promobee.service.ComplianceService
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
open class ComplianceController@Autowired constructor(
    private val complianceService: ComplianceService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/recipientUnblockingPartial/{statusType}/{month}/{year}")
    fun recipientUnblockingPartial(@PathVariable statusType : String , @PathVariable month : String ,@PathVariable year : String   ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = complianceService.recipientUnblockingPartial(statusType,month,year)
        return ResponseEntity(data, HttpStatus.OK)
    }











}