package com.squer.promobee.controller

import com.squer.promobee.api.v1.enums.UploadTypeEnum
import com.squer.promobee.controller.dto.GRNAckDTO
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.GrnService
import com.squer.promobee.service.UploadLogService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@Slf4j
open class GRNController @Autowired constructor(
        private val grnService: GrnService,
        private val uploadLogService: UploadLogService
){

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/acknowledge")
    open fun getUnacknowledgeGRNs(): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val grns = grnService.getUnacknowledgeData()
        return ResponseEntity(grns, HttpStatus.OK)
    }

    @PutMapping("/rejectAcknowledge/{id}/{reason}")
    open fun rejectAcknowledge(@PathVariable id: String, @PathVariable reason: String): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        grnService.rejectAcknowledge(id, reason, user.id)
        return ResponseEntity(null, HttpStatus.OK)
    }

    @PutMapping("/approveAcknowledge")
    open fun approveAcknowledge(@RequestBody data: GRNAckDTO){
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        grnService.approveAcknowledge(data, user.id)
    }

    @GetMapping("/getUploadLog")
    open fun getUploadLog(): ResponseEntity<*>{
        val data = uploadLogService.getUploadLogByStatusId(UploadTypeEnum.GRN.id)
        return ResponseEntity(data, HttpStatus.OK)
    }
}