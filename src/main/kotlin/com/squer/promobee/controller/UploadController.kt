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
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping("/getAllUploadLog/{typeId}")
    open fun getAllUploadLog(@PathVariable typeId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.getAllUploadLog(typeId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }

    @PostMapping("/transporterUpload")
    fun transporterUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.transporterUpload(dto)
    }



    @GetMapping("/transportExcelData/{uplId}")
    open fun transportExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.transportExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }

    @GetMapping("/transportErrorData/{uplId}")
    open fun transportErrorData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.transportExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }




    @PostMapping("/grnUpload")
    fun grnUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.grnUpload(dto)
    }

    @GetMapping("/grnExcelData/{uplId}")
    open fun grnExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.grnExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }


    @PostMapping("/recipientUpload")
    fun recipientUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.recipientUpload(dto)
    }


    @GetMapping("/recipientExcelData/{uplId}")
    open fun recipientExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.recipientExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }


    @PostMapping("/virtualUpload")
    fun virtualUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.virtualUpload(dto)
    }

    @GetMapping("/virtualSampleExcelData/{uplId}")
    open fun virtualSampleExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.virtualSampleExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }


    @GetMapping("/getRecipientByCode/{code}")
    fun getRecipientByCode(@PathVariable code: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = uploadService.getRecipientByCode(code)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getTransporterByName/{name}")
    fun getTransporterByName(@PathVariable name: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = uploadService.getTransporterByName(name)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDoctorsByCode/{code}")
    fun getDoctorsByCode(@PathVariable code: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = uploadService.getDoctorsByCode(code)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/invoiceUpload")
    fun invoiceUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.invoiceUpload(dto)
    }


    @GetMapping("/invoiceExcelData/{uplId}")
    open fun invoiceExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.invoiceExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }






}