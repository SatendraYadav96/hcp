package com.squer.promobee.controller




import com.squer.promobee.controller.dto.SaveNonComplianceAdminRemarkDTO
import com.squer.promobee.controller.dto.SaveOverSamplingDTO
import com.squer.promobee.controller.dto.SaveRecipientBlockedmasterDTO
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


    @GetMapping("/optimaMailLogs/{type}/{month}/{year}")
    fun optimaMailLogs(@PathVariable type : String , @PathVariable month : String ,@PathVariable year : String   ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = complianceService.optimaMailLogs(type,month,year)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/overSamplingDetails/{month}/{year}")
    fun overSamplingDetails(@PathVariable month : String ,@PathVariable year : String   ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = complianceService.overSamplingDetails(month,year)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/masterBlockedList/{year}")
    fun masterBlockedList(@PathVariable year : String   ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = complianceService.masterBlockedList(year)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/SaveMasterBlockedRecipient")
    fun saveMasterBlockedRecipient(@RequestBody blockRecp :List<SaveRecipientBlockedmasterDTO>  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = complianceService.saveMasterBlockedRecipient(blockRecp)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/saveOverSampling")
    fun saveOverSampling(@RequestBody comp :List<SaveOverSamplingDTO>  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = complianceService.saveOverSampling(comp)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/saveNonComplianceAdminRemark")
    fun saveNonComplianceAdminRemark(@RequestBody nonComp :List<SaveNonComplianceAdminRemarkDTO>  ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = complianceService.saveNonComplianceAdminRemark(nonComp)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/overSamplingDetailsData/{month}/{year}/{ffTerritory}/{personCode}")
    fun overSamplingDetailsData(@PathVariable month : String ,@PathVariable year : String ,@PathVariable ffTerritory : String  ,@PathVariable personCode : String   ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = complianceService.overSamplingDetailsData(month,year,ffTerritory,personCode)
        return ResponseEntity(data, HttpStatus.OK)
    }
















}