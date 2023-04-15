package com.squer.promobee.controller



import com.squer.promobee.controller.dto.CostCenterDTO
import com.squer.promobee.controller.dto.VendorDTO
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.MasterService
import com.squer.promobee.service.repository.domain.*
import liquibase.pro.packaged.bu
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import lombok.extern.slf4j.Slf4j
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@Slf4j
open class MasterController@Autowired constructor(
    private val masterService: MasterService
){


    private val log = LoggerFactory.getLogger(javaClass)


    //VENDOR MASTER CONTROLLER

    @GetMapping("/getVendor/{status}")
    fun getVendor(@PathVariable status: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getVendor(status)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getVendorById/{id}")
    fun getVendorById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getVendorById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }



    @PostMapping("/addVendor")
    open fun addVendor(@RequestBody vnd: Vendor): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val insertVendor = masterService.addVendor(vnd)
        return ResponseEntity(insertVendor, HttpStatus.OK)
    }

    @PutMapping("/editVendor/{id}")
    open fun editVendor(@PathVariable id: String,@RequestBody vnd: Vendor): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editVendor(vnd)
        return ResponseEntity(data, HttpStatus.OK)
    }





    //COST CENTER MASTER CONTROLLER

    @GetMapping("/getCostCenter/{status}")
    fun getCostCenter(@PathVariable status: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getCostCenter(status)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/addCostCenter")
    open fun addCostCenter(@RequestBody ccm: CostCenter): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val insertCostCenter = masterService.addCostCenter(ccm)
        return ResponseEntity(insertCostCenter, HttpStatus.OK)
    }


    @PutMapping("/editCostCenter/{ccmId}")
    open fun editCostCenter(@PathVariable ccmId: String,@RequestBody ccm: CostCenterDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editCostCenter(ccm)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getCostCenterById/{id}")
    fun getCostCenterById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getCostCenterById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }


    //SAMPLE MASTER CONTROLLER


    @GetMapping("/getSample/{status}")
    fun getSample(@PathVariable status: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getSample(status)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/addSample")
    open fun addSample(@RequestBody smp: SampleMaster): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val insertSample = masterService.addSample(smp)
        return ResponseEntity(insertSample, HttpStatus.OK)
    }

    @PutMapping("/editSample/{id}")
    open fun editSample(@PathVariable id: String,@RequestBody smp: SampleMaster): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editSample(smp)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getSampleById/{id}")
    fun getSampleById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getSampleById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }



    // DROPDOWN CONTROLLER

    @GetMapping("/getBusinessUnitDropdown")
    fun getBusinessUnitDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getBusinessUnitDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getBrandDropdown")
    fun getBrandDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getBrandDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }

















}