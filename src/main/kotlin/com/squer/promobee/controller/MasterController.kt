package com.squer.promobee.controller



import com.squer.promobee.controller.dto.FieldForceDTO
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.MasterService
import com.squer.promobee.service.repository.domain.*
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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
    open fun addCostCenter(@RequestBody ccm: MasterCostCenter): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val insertCostCenter = masterService.addCostCenter(ccm)
        return ResponseEntity(insertCostCenter, HttpStatus.OK)
    }


    @PutMapping("/editCostCenter/{id}")
    open fun editCostCenter(@PathVariable id: String,@RequestBody ccm: MasterCostCenter): ResponseEntity<*>{
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


    @GetMapping("/getDivisionDropdown")
    fun getDivisionDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getDivisionDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getTeamDropdown")
    fun getTeamDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getTeamDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getCostCenterDropdown")
    fun getCostCenterDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getCostCenterDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getItemCodeDropdown")
    fun getItemCodeDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getItemCodeDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getRecipientDropdown")
    fun getRecipientDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getRecipientDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getInvoiceDropdown")
    fun getInvoiceDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getInvoiceDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getTransporter")
    fun getTransporter(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getTransporter()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getLegalEntityDropdown")
    fun getLegalEntityDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getLegalEntityDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getRecipientDesignationDropdown")
    fun getRecipientDesignationDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getRecipientDesignationDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getUserDesignationDropdown")
    fun getUserDesignationDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getUserDesignationDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getUserDropdown")
    fun getUserDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getUserDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getApproverDropdown")
    fun getApproverDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getApproverDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getOwnerDropdown")
    fun getOwnerDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getOwnerDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }




    // BUSINESS UNIT CONTROLLER

    @GetMapping("/getBusinessUnit/{status}")
    fun getBusinessUnit(@PathVariable status: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getBusinessUnit(status)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getBusinessUnitById/{id}")
    fun getBusinessUnitById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getBusinessUnitById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PutMapping("/editBusinessUnit")
    open fun editBusinessUnit(@RequestBody bu: BU): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editBusinessUnit(bu)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/addBusinessUnit")
    open fun addBusinessUnit(@RequestBody bu: BU): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addBusinessUnit(bu)
        return ResponseEntity(data, HttpStatus.OK)
    }


    // TEAM CONTROLLER

    @GetMapping("/getTeam/{status}")
    fun getTeam(@PathVariable status: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getTeam(status)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getBrandByTeamId/{teamId}")
    fun getBrandByTeamId(@PathVariable teamId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getBrandByTeamId(teamId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getTeamById/{id}")
    fun getTeamById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getTeamById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PutMapping("/editTeam")
    open fun editTeam(@RequestBody tem: MasterTeam): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editTeam(tem)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/addTeam")
    open fun addTeam(@RequestBody tem: MasterTeam): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addTeam(tem)
        return ResponseEntity(data, HttpStatus.OK)
    }


    //USER CONTROLLER

    @GetMapping("/getUser/{status}")
    fun getUser(@PathVariable status: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getUser(status)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getUserById/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getUserById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PutMapping("/editUser")
    open fun editUser(@RequestBody usr: MasterUsers): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editUser(usr)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/addUser")
    open fun addUser(@RequestBody usr: MasterUsers): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addUser(usr)
        return ResponseEntity(data, HttpStatus.OK)
    }


    //BRAND CONTROLLER

    @GetMapping("/getBrand/{status}")
    fun getBrand(@PathVariable status: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getBrand(status)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getBrandById/{id}")
    fun getBrandById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getBrandById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PutMapping("/editBrand")
    open fun editBrand(@RequestBody brd: MasterBrand): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editBrand(brd)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/addBrand")
    open fun addBrand(@RequestBody brd: MasterBrand): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addBrand(brd)
        return ResponseEntity(data, HttpStatus.OK)
    }


    // RECIPIENT CONTROLLER

    @PostMapping("/getFieldForce")
    fun getFieldForce(@RequestBody ff : FieldForceDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getFieldForce(ff)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getFieldForceHistory/{id}")
    fun getFieldForceHistory(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getFieldForceHistory(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getFieldForceById/{id}")
    fun getFieldForceById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getFieldForceById(id)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PutMapping("/editFieldForce")
    open fun editFieldForce(@RequestBody ff: MasterFF): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editFieldForce(ff)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/addFieldForce")
    open fun addFieldForce(@RequestBody ff: MasterFF): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addFieldForce(ff)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PutMapping("/deleteUserMapping/{id}")
    fun deleteUserMapping(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.deleteUserMapping(id)
        return ResponseEntity(data, HttpStatus.OK)
    }


























}