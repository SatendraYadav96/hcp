package com.squer.promobee.controller


import com.squer.promobee.controller.dto.FileUploadDto
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.UploadService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@Slf4j
open class UploadController@Autowired constructor(
    private val uploadService: UploadService
) {

    private val log = LoggerFactory.getLogger(javaClass)


    @GetMapping("/getGrnUploadLog")
    open fun getGrnUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var grnUpl = uploadService.getGrnUploadLog()
        return ResponseEntity(grnUpl, HttpStatus.OK)
    }


    @GetMapping("/getTransporterUploadLog")
    open fun getTransporterUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var trnUpl = uploadService.getTransporterUploadLog()
        return ResponseEntity(trnUpl, HttpStatus.OK)
    }


    @GetMapping("/getInvoiceUploadLog")
    open fun getInvoiceUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.getInvoiceUploadLog()
        return ResponseEntity(invUpl, HttpStatus.OK)
    }

    @PostMapping("/transporterUpload")
    fun transporterUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.transporterUpload(dto)
    }





}