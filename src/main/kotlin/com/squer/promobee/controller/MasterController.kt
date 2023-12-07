package com.squer.promobee.controller



import com.squer.promobee.api.v1.enums.UserRoleEnum
import com.squer.promobee.controller.dto.FieldForceDTO
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.MasterService
import com.squer.promobee.service.repository.domain.*
import lombok.extern.slf4j.Slf4j
import org.mybatis.spring.SqlSessionTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Slf4j
open class MasterController@Autowired constructor(
    private val masterService: MasterService
){


    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var sqlSessionTemplate: SqlSessionTemplate


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


    @PostMapping("/addVendors")
    open fun addVendors(@RequestBody vnd: Vendor): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data0: MutableMap<String, Any> = mutableMapOf()

        var errorMap: MutableMap<String, String> = HashMap()

        lateinit var jsonResult : Map<String, Any>

        var vendorCode = 0;
        vnd.code?.let { data0.put("code", it) }

        vendorCode = sqlSessionTemplate.selectOne("VendorMapper.vendorExist",data0)

        if(vendorCode > 0 ){
            errorMap["message"] = "Vendor Code Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)
        }else {

            var data: MutableMap<String, Any> = mutableMapOf()
            data.put("id", UUID.randomUUID().toString())
            vnd.name?.let { data.put("name", it.uppercase()) }
            vnd.name?.let { data.put("ciName", it.lowercase()) }
            vnd.code?.let { data.put("code", it.uppercase()) }
            vnd.addressLine1?.let { data.put("addressLine1", it) }
            vnd.addressLine2?.let { data.put("addressLine2", it) }
            vnd.city?.let { data.put("city", it) }
            vnd.state?.let { data.put("state", it) }
            vnd.zip?.let { data.put("zip", it) }
            vnd.active?.let { data.put("active", it) }
            data.put("createdBy", user.id)
            data.put("updatedBy", user.id)

            sqlSessionTemplate.insert("VendorMapper.addVendor", data)

            errorMap["message"] = "Vendor created successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)

        }

    }

    @PutMapping("/editVendor/{id}")
    open fun editVendor(@PathVariable id: String,@RequestBody vnd: Vendor): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editVendor(vnd)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PutMapping("/editVendors/{id}")
    open fun editVendors(@PathVariable id: String,@RequestBody vnd: Vendor): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var errorMap: MutableMap<String, String> = HashMap()


            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("id", vnd.id)
            vnd.name?.let { data.put("name", it.uppercase()) }
            vnd.name?.let { data.put("ciName", it.lowercase()) }
            vnd.code?.let { data.put("code", it.uppercase()) }
            vnd.addressLine1?.let { data.put("addressLine1", it) }
            vnd.addressLine2?.let { data.put("addressLine2", it) }
            vnd.city?.let { data.put("city", it) }
            vnd.state?.let { data.put("state", it) }
            vnd.zip?.let { data.put("zip", it) }
            vnd.active?.let { data.put("active", it) }
            data.put("updatedBy", user.id)

            sqlSessionTemplate.update("VendorMapper.editVendor",data)

            errorMap["message"] = "Vendor updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)


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


    @PostMapping("/addCostCenters")
    open fun addCostCenters(@RequestBody ccm: MasterCostCenter): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        var errorMap: MutableMap<String, String> = HashMap()

        var costCenter = mutableListOf<CostCenter>()

        var data0 : MutableMap<String, Any> = mutableMapOf()

        data0.put("code", ccm.code!!)

        costCenter = sqlSessionTemplate.selectList<CostCenter>("CostCenterMapper.checkCostCenterCode",data0)


        if(costCenter.size > 0){
            errorMap["message"] = "Cost Center Code Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)
        }else{
            var ccmId5 = UUID.randomUUID().toString()

            data.put("id",ccmId5 )
            ccm.name?.let { data.put("name", it.uppercase()) }
            ccm.name?.let { data.put("ciName", it.lowercase()) }
            ccm.code?.let { data.put("code", it.uppercase()) }
            ccm.active?.let { data.put("active", it) }
            data.put("createdBy", user.id )
            data.put("updatedBy", user.id)



            sqlSessionTemplate.insert("CostCenterMapper.addCostCenter",data)



            var i = 0

            ccm.brandId.forEach {
                var cbr = CostCenterBrand()
                var cbrId = UUID.randomUUID().toString()

                data.put("id", cbrId)
                data.put("ccmId", ccmId5)
                data.put("brandId",ccm.brandId.get(i))

                sqlSessionTemplate.insert("CostCenterBrandMapper.addCostCenterBrand", data)

                i++
            }

            errorMap["message"] = "Cost Center created successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)
        }
    }


    @PutMapping("/editCostCenter/{id}")
    open fun editCostCenter(@PathVariable id: String , @RequestBody ccm: MasterCostCenter): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editCostCenter(ccm)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PutMapping("/editCostCenters/{id}")
    open fun editCostCenters(@PathVariable id: String , @RequestBody ccm: MasterCostCenter): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var errorMap: MutableMap<String, String> = HashMap()

        var costCenter = CostCenter()


            var data: MutableMap<String, Any> = mutableMapOf()

            ccm.id?.let { data.put("id", it) }
            ccm.name?.let { data.put("name", it.uppercase()) }
            ccm.name?.let { data.put("ciName", it.lowercase()) }
            ccm.code?.let { data.put("code", it.uppercase()) }
            ccm.active?.let { data.put("active", it) }
            data.put("updatedBy", user.id)

            sqlSessionTemplate.update("CostCenterMapper.editCostCenter",data)
            var ccmId2 = ccm.id

            var data0 : MutableMap<String, Any> = mutableMapOf()

            data0.put("ccmId",ccmId2)

            sqlSessionTemplate.delete("CostCenterMapper.deleteCostCenterBrand",data0)


            var cbr = CostCenterBrand()

            var i = 0
            ccm.brandId.forEach {



                var cbrId = UUID.randomUUID().toString()

                data.put("id", cbrId)
                ccmId2?.let { data.put("ccmId", it) }
//        data.put("brandId",NamedSquerEntity(ccm.brandId?.id.toString(),""))
                // ccm.brandId?.let { data.put("brandId", it) }
                //data.put("brandId",NamedSquerEntity(ccm.brandId?id.toString(),""))
                data.put("brandId", ccm.brandId.get(i))

                sqlSessionTemplate.insert("CostCenterBrandMapper.editCostCenterBrand",data)

                i++
            }
            errorMap["message"] = "Cost Center updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)

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



    @PostMapping("/addSamples")
    open fun addSamples(@RequestBody smp: SampleMaster): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var errorMap: MutableMap<String, String> = HashMap()

        var sample = mutableListOf<SampleMaster>()

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("lmid",smp.lmid!!)

        sample = sqlSessionTemplate.selectList<SampleMaster>("SampleMasterMapper.getSampleByLmid",data0)

        if(sample.size > 0){
            errorMap["message"] = "Sample Code Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)
        } else {
            var data: MutableMap<String, Any> = mutableMapOf()
            var smp1 = UUID.randomUUID().toString()

            data.put("id",smp1 )
            smp.lmid?.let { data.put("lmid", it.uppercase()) }
            smp.name?.let { data.put("name", it.uppercase()) }
            smp.name?.let { data.put("ciName", it.lowercase()) }
            smp.description?.let { data.put("description", it) }
            data.put("brandId", NamedSquerEntity(smp.brandId?.id.toString(),""))
            smp.packSize?.let { data.put("packSize", it) }
            smp.active?.let { data.put("active", it) }
            smp.hsnCode?.let { data.put("hsnCode", it) }
            smp.cap?.let { data.put("cap", it) }
            data.put("createdBy", user.id )
            data.put("updatedBy", user.id)


            sqlSessionTemplate.insert("SampleMasterMapper.addSample",data)


            errorMap["message"] = "Sample created successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)
        }
    }


    @PutMapping("/editSample/{id}")
    open fun editSample(@PathVariable id: String,@RequestBody smp: SampleMaster): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.editSample(smp)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PutMapping("/editSamples/{id}")
    open fun editSamples(@PathVariable id: String,@RequestBody smp: SampleMaster): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var errorMap: MutableMap<String, String> = HashMap()

        var sample = SampleMaster()

            var data: MutableMap<String, Any> = mutableMapOf()

            smp.id?.let { data.put("id", it) }

            smp.lmid?.let { data.put("lmid", it.uppercase()) }
            smp.name?.let { data.put("name", it.uppercase()) }
            smp.name?.let { data.put("ciName", it.lowercase()) }
            smp.description?.let { data.put("description", it) }
            data.put("brandId",NamedSquerEntity(smp.brandId?.id.toString(),""))
            smp.packSize?.let { data.put("packSize", it) }
            smp.active?.let { data.put("active", it) }
            smp.cap?.let { data.put("cap", it) }
            data.put("updatedBy", user.id)



            sqlSessionTemplate.update("SampleMasterMapper.editSample",data)

            errorMap["message"] = "Sample updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)

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

    @GetMapping("/getTseDropdown")
    fun getTseDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.getTseDropdown()
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

    @PutMapping("/editBusinessUnits")
    open fun editBusinessUnits(@RequestBody bu: BU): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var businessUnit = BU()

        var errorMap: MutableMap<String, String> = HashMap()



            var data: MutableMap<String, Any> = mutableMapOf()


            data.put("id", bu.id)
            bu.name?.let { data.put("name", it.uppercase()) }
            bu.name?.let { data.put("ciName", it.lowercase()) }
            bu.active?.let { data.put("active", it) }
            data.put("updatedBy",user.id)




            sqlSessionTemplate.update("BUMapper.editBusinessUnit",data)

            errorMap["message"] = "Team updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)



    }


    @PostMapping("/addBusinessUnit")
    open fun addBusinessUnit(@RequestBody bu: BU): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addBusinessUnit(bu)

        return ResponseEntity(data, HttpStatus.OK)


    }


    @PostMapping("/addBusinessUnits")
    open fun addBusinessUnits(@RequestBody bu: BU): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var businessUnit = mutableListOf<BU>()

        var data0: MutableMap<String, Any> = mutableMapOf()

        var errorMap: MutableMap<String, String> = HashMap()

        data0.put("code",bu.code!!)

        businessUnit = sqlSessionTemplate.selectList<BU>("BUMapper.checkBUCode",data0)

        if(businessUnit.size > 0 ){
            errorMap["message"] = "Team Code Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)
        }else {
            var data: MutableMap<String, Any> = mutableMapOf()

            var buId = UUID.randomUUID().toString()

            data.put("id",buId)
            bu.name?.let { data.put("name", it.uppercase()) }
            bu.name?.let { data.put("ciName", it.lowercase()) }
            bu.code?.let { data.put("code", it) }
            bu.active?.let { data.put("active", it) }
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)


            sqlSessionTemplate.insert("BUMapper.addBusinessUnit",data)
            errorMap["message"] = "Team created successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)
        }

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


    @PutMapping("/editTeams")
    open fun editTeams(@RequestBody tem: MasterTeam): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var team = Team()


        var errorMap: MutableMap<String, String> = HashMap()

            var data: MutableMap<String, Any> = mutableMapOf()

            // add team

            data.put("id",tem.id)
            tem.name?.let { data.put("name", it) }
            tem.name?.let { data.put("ciName", it.lowercase()) }
            tem.code?.let { data.put("code", it) }
            tem.active?.let { data.put("active", it) }
            tem.division?.let { data.put("division", it.id) }
            data.put("updatedBy",user.id)

            sqlSessionTemplate.update("TeamMapper.editTeam",data)

            //delete existing brand mapped to team

            data.put("id",tem.id)

            sqlSessionTemplate.delete("TeamBrandMapper.deleteBrandByTeamId",data)


            // mapped requested brand to team

            var tbr =  mutableListOf<MutableList<TeamBrand>>()

            var i = 0

            tem.brand.forEach {



                var tbrId = UUID.randomUUID().toString()

                data.put("id",tbrId)
                data.put("teamId",tem.id)
                data.put("brandId",tem.brand.get(i))

                sqlSessionTemplate.insert("TeamBrandMapper.addBrandByTeamId",data)

                i++
            }

            //delete existing entity mapped to team

            data.put("id",tem.id)

            sqlSessionTemplate.delete("TeamLegalEntityMapper.deleteEntityByTeamId",data)

            //mapped requested entity to team

            var tet = TeamLegalEntity()

            i = 0

            tem.ety.forEach {


                var tetId = UUID.randomUUID().toString()

                data.put("id",tetId)
                data.put("team",tem.id)
                data.put("legalEntity",tem.ety.get(i))

                sqlSessionTemplate.insert("TeamLegalEntityMapper.addEntityByTeamId",data)

                i++

            }
            errorMap["message"] = "Sub Team Updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)



    }


    @PostMapping("/addTeam")
    open fun addTeam(@RequestBody tem: MasterTeam): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addTeam(tem)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/addTeams")
    open fun addTeams(@RequestBody tem: MasterTeam): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var team = mutableListOf<Team>()

        var data0: MutableMap<String, Any> = mutableMapOf()
        var errorMap: MutableMap<String, String> = HashMap()

        data0.put("code",tem.code!!)

        team = sqlSessionTemplate.selectList<Team>("TeamMapper.checkTeamCode",data0)

        if(team.size > 0){

            errorMap["message"] = "Sub Team Code Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)

        } else {
            // add team

            var data: MutableMap<String, Any> = mutableMapOf()
            var temId = UUID.randomUUID().toString()

            data.put("id",temId)
            tem.name?.let { data.put("name", it) }
            tem.name?.let { data.put("ciName", it.lowercase()) }
            tem.code?.let { data.put("code", it) }
            tem.active?.let { data.put("active", it) }
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            tem.division?.id.let { it?.let { it1 -> data.put("division", it1) } }

            sqlSessionTemplate.insert("TeamMapper.addTeam",data)



            // mapped requested brand to team

            var tbr =  TeamBrand()

            var i = 0

            tem.brand.forEach {



                var tbrId = UUID.randomUUID().toString()

                data.put("id",tbrId)
                data.put("teamId",temId)
                data.put("brandId",tem.brand.get(i))

                sqlSessionTemplate.insert("TeamBrandMapper.addBrandByTeamId",data)

                i++

            }



            //mapped requested entity to team

            var tet = TeamLegalEntity()

            i = 0

            tem.ety.forEach {


                var tetId = UUID.randomUUID().toString()

                data.put("id",tetId)
                data.put("team",temId)
                data.put("legalEntity",tem.ety.get(i))

                sqlSessionTemplate.insert("TeamLegalEntityMapper.addEntityByTeamId",data)

                i++

            }


            println("Team Added Successfully !")

            errorMap["message"] = "Sub Team created successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)
        }
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


    @PutMapping("/editUsers")
    open fun editUsers(@RequestBody usr: MasterUsers): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var errorMap: MutableMap<String, String> = HashMap()

        var users = User()

            var data: MutableMap<String, Any> = mutableMapOf()

            // update user

            data.put("id",usr.id)
            usr.email?.let { data.put("email", it) }
            usr.legalEntity?.let { data.put("legalEntity", it.id) }
            usr.userDesignation?.let { data.put("userDesignation", it.id) }
            usr.userStatus?.let { data.put("userStatus", it.id) }
            usr.approver?.let { data.put("approver", it) }

            sqlSessionTemplate.update("UsersMasterMapper.editUser",data)


            var bbr = BrandManager()

            // delete existing brand mapped to user


            data.put("id",usr.id)

            sqlSessionTemplate.delete("BrandManagerMapper.deleteBrandByUserId",data)

            var i = 0

            usr.brand.forEach {

                var bbrId = UUID.randomUUID().toString()

                data.put("id",bbrId)
                data.put("userId",usr.id)
                data.put("brandId",usr.brand.get(i))

                sqlSessionTemplate.insert("BrandManagerMapper.addBrandByUserId",data)

                i++

            }


            println("User Updated Successfully !")
            errorMap["message"] = "User updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)


    }



    @PostMapping("/addUser")
    open fun addUser(@RequestBody usr: MasterUsers): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addUser(usr)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/addUsers")
    open fun addUsers(@RequestBody usr: MasterUsers): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var errorMap: MutableMap<String, String> = HashMap()
        var usrCode = mutableListOf<User>()
        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("code",usr.employeeCode!!)

        usrCode = sqlSessionTemplate.selectList<User>("UserMapper.checkUserCode",data0)

        var usrLogin = mutableListOf<User>()
        var data1: MutableMap<String, Any> = mutableMapOf()

        data1.put("login",usr.username!!)

        usrLogin = sqlSessionTemplate.selectList<User>("UserMapper.checkUserLogin",data1)


        if(usrCode.size > 0){
            errorMap["message"] = "User's Employee Code Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)

        } else if (usrLogin.size > 0){
            errorMap["message"] = "User's LoginId Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)
        } else{
            // add user
            var data: MutableMap<String, Any> = mutableMapOf()
            var usrId = UUID.randomUUID().toString()

            val ldt: LocalDateTime = LocalDateTime.now()
            var actFrm = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt)
            System.out.println(ldt)

            data.put("id",usrId)
            usr.name?.let { data.put("name", it.uppercase()) }
            usr.name?.let { data.put("ciName", it.lowercase()) }
            usr.username?.let { data.put("username", it) }
            usr.employeeCode?.let { data.put("employeeCode", it) }
            usr.userDesignation?.let { data.put("userDesignation", it.id) }
            data.put("activeFrom", actFrm)
//        usr.activeTo?.let { data.put("activeTo", it) }
            usr.userStatus?.let { data.put("userStatus", it.id) }
            usr.legalEntity?.let { data.put("legalEntity", it.id) }
            usr.email?.let { data.put("email", it) }
            // usr.lastLoggedIn?.let { data.put("lastLoggedIn", it) }
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            usr.appBu?.let { data.put("appBu", it.id) }
            //usr.userRecipientId?.let { data.put("userRecipientId", it) }
            usr.approver?.let { data.put("approver", it) }

            sqlSessionTemplate.insert("UsersMasterMapper.addUser",data)




            //map brand to new user

            var bbr = BrandManager()

            var i = 0

            usr.brand.forEach {

                var bbrId = UUID.randomUUID().toString()

                data.put("id",bbrId)
                data.put("userId",usrId)
                data.put("brandId",usr.brand.get(i))

                sqlSessionTemplate.insert("BrandManagerMapper.addBrandByUserId",data)

                i++

            }


            println("User Added Successfully !")

            errorMap["message"] = "User created successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)


        }

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


    @PutMapping("/editBrands")
    open fun editBrands(@RequestBody brd: MasterBrand): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var errorMap: MutableMap<String, String> = HashMap()

        if(user.userDesignation!!.id == UserRoleEnum.PRODUCT_MANAGER_ID.id ){
            var data: MutableMap<String, Any> = mutableMapOf()
            var brand = BrandMaster()


            // delete existing user mapped to brand

            data.put("id",brd.id)

            sqlSessionTemplate.delete("BrandManagerMapper.deleteBrandByBrandId",data)

            // map selected user to brand

            var bbr = BrandManager()

            var i = 0

            brd.user.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                var bbrId = UUID.randomUUID().toString()

                data.put("id",bbrId)
                data.put("userId",brd.user.get(i))
                data.put("brandId",brd.id)

                sqlSessionTemplate.insert("BrandManagerMapper.addBrandByBrandId",data)

                i++


            }

            println("Brand Owner Updated Successfully !")
            errorMap["message"] = "Brand Owner updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)

        } else{
            var brand = BrandMaster()

            var data: MutableMap<String, Any> = mutableMapOf()

            // update brand

            data.put("id",brd.id)
            brd.name?.let { data.put("name", it.uppercase()) }
            brd.name?.let { data.put("ciName", it.lowercase()) }
            brd.active?.let { data.put("active", it) }
            data.put("updatedBy",user.id)

            sqlSessionTemplate.update("BrandMasterMapper.editBrand",data)

            // delete existing division mapped to brand

            data.put("id",brd.id)

            sqlSessionTemplate.delete("DivisionBrandMapper.deleteBrandByBrandId",data)

            //map selected division to brand

            var dbr = DivisionBrand()

            var dbrId = UUID.randomUUID().toString()

            data.put("id",dbrId)
            brd.division?.let { data.put("divisionId", it.id) }
            data.put("brandId", brd.id)

            sqlSessionTemplate.insert("DivisionBrandMapper.addBrandByBrandId",data)


            // delete existing user mapped to brand

            data.put("id",brd.id)

            sqlSessionTemplate.delete("BrandManagerMapper.deleteBrandByBrandId",data)

            // map selected user to brand

            var bbr = BrandManager()

            var i = 0

            brd.user.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                var bbrId = UUID.randomUUID().toString()

                data.put("id",bbrId)
                data.put("userId",brd.user.get(i))
                data.put("brandId",brd.id)

                sqlSessionTemplate.insert("BrandManagerMapper.addBrandByBrandId",data)

                i++


            }


            // delete existing team mapped to brand

            data.put("id",brd.id)

            sqlSessionTemplate.delete("TeamBrandMapper.deleteBrandByBrandId",data)

            // map requested team to brand

            var tbr = TeamBrand()

            i = 0

            brd.team.forEach {

                var data: MutableMap<String, Any> = mutableMapOf()

                var tbrId = UUID.randomUUID().toString()

                data.put("id",tbrId)
                data.put("teamId",brd.team.get(i))
                data.put("brandId",brd.id)

                sqlSessionTemplate.insert("TeamBrandMapper.addBrandByBrandId",data)

                i++



            }

            // delete existing cost center mapped to brand

            data.put("id",brd.id)

            sqlSessionTemplate.delete("CostCenterBrandMapper.deleteBrandByBrandId",data)


            // map requested cost center  to brand

            var cbr = CostCenterBrand()

            i = 0

            brd.costCenter.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                var cbrId = UUID.randomUUID().toString()

                data.put("id",cbrId)
                data.put("costCenter",brd.costCenter.get(i))
                data.put("brand",brd.id)

                sqlSessionTemplate.insert("CostCenterBrandMapper.addBrandByBrandId",data)

                i++

            }


            println("Brand Updated Successfully !")
            errorMap["message"] = "Brand updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)
        }






    }



    @PostMapping("/addBrand")
    open fun addBrand(@RequestBody brd: MasterBrand): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addBrand(brd)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/addBrands")
    open fun addBrands(@RequestBody brd: MasterBrand): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var errorMap: MutableMap<String, String> = HashMap()
        var data0: MutableMap<String, Any> = mutableMapOf()


        var brand = mutableListOf<BrandMaster>()

        data0.put("code",brd.code!!)

        brand = sqlSessionTemplate.selectList<BrandMaster>("BrandMasterMapper.checkBrandCode",data0)

        if(brand.size > 0){
            errorMap["message"] = "Brand Code Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)
        } else{
            // add brand

            var data: MutableMap<String, Any> = mutableMapOf()

            var brdId = UUID.randomUUID().toString()

            data.put("id",brdId)
            brd.name?.let { data.put("name", it.uppercase()) }
            brd.name?.let { data.put("ciName", it.lowercase()) }
            brd.code?.let { data.put("code", it) }
            brd.active?.let { data.put("active", it) }
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)

            sqlSessionTemplate.insert("BrandMasterMapper.addBrand",data)




            //map selected division to brand

            var dbr = DivisionBrand()

            var dbrId = UUID.randomUUID().toString()

            data.put("id",dbrId)
            brd.division?.let { data.put("divisionId", it.id) }
            data.put("brandId", brdId)

            sqlSessionTemplate.insert("DivisionBrandMapper.addBrandByBrandId",data)




            // map selected user to brand

            var bbr = BrandManager()

            var i = 0

            brd.user.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                var bbrId = UUID.randomUUID().toString()

                data.put("id",bbrId)
                data.put("userId",brd.user.get(i))
                data.put("brandId",brdId)

                sqlSessionTemplate.insert("BrandManagerMapper.addBrandByBrandId",data)

                i++


            }




            // map requested team to brand

            var tbr = TeamBrand()

            i = 0

            brd.team.forEach {

                var data: MutableMap<String, Any> = mutableMapOf()

                var tbrId = UUID.randomUUID().toString()

                data.put("id",tbrId)
                data.put("teamId",brd.team.get(i))
                data.put("brandId",brdId)

                sqlSessionTemplate.insert("TeamBrandMapper.addBrandByBrandId",data)

                i++



            }




            // map requested cost center  to brand

            var cbr = CostCenterBrand()

            i = 0

            brd.costCenter.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                var cbrId = UUID.randomUUID().toString()

                data.put("id",cbrId)
                data.put("costCenter",brd.costCenter.get(i))
                data.put("brand",brdId)

                sqlSessionTemplate.insert("CostCenterBrandMapper.addBrandByBrandId",data)

                i++

            }


            println("Brand Added Successfully !")
            errorMap["message"] = "Brand created successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)

        }


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

    @PutMapping("/editFieldForces")
    open fun editFieldForces(@RequestBody ff: MasterFF): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var errorMap: MutableMap<String, String> = HashMap()


        var recipient = Recipient()

            var data: MutableMap<String, Any> = mutableMapOf()


            // update ff

            data.put("id", ff.id)
            ff.name?.let { data.put("name", it.uppercase()) }
            ff.name?.let { data.put("ciName", it.lowercase()) }
            ff.code?.let { data.put("code", it) }
            ff.address?.let { data.put("address", it) }
            ff.city?.let { data.put("city", it) }
            ff.state?.let { data.put("state", it) }
            ff.zip?.let { data.put("zip", it) }
            ff.email?.let { data.put("email", it) }
            ff.mobile?.let { data.put("mobile", it) }
            ff.designation?.let { data.put("designation", it.id) }
            ff.headQuarter?.let { data.put("headQuarter", it) }
            ff.zone?.let { data.put("zone", it) }
            ff.joiningDate?.let { data.put("joiningDate", it) }
            ff.team?.let { data.put("team", it.id) }
            ff.cfa?.let { data.put("cfa", it) }
            ff.recipientStatus?.let { data.put("recipientStatus", it.id) }
            data.put("updatedBy",user.id)
            ff.loginId?.let { data.put("loginId", it) }
            ff.gender?.let { data.put("gender", it) }
            ff.workId?.let { data.put("workId", it) }
            ff.emailRBM?.let { data.put("emailRBM", it) }
            ff.emailAM?.let { data.put("emailAM", it) }
            ff.businessUnit?.let { data.put("businessUnit", it.id) }

            sqlSessionTemplate.update("FieldForceMapper.editFieldForce",data)


            //insert ff history

            var recpHist =  RecipientHistory()
            var repHisId =  UUID.randomUUID().toString()

            data.put("id",repHisId)
            data.put("recipientId",ff.id)
            ff.name?.let { data.put("name", it.uppercase()) }
            ff.name?.let { data.put("ciName", it.lowercase()) }
            ff.code?.let { data.put("code", it) }
            ff.address?.let { data.put("address", it) }
            ff.city?.let { data.put("city", it) }
            ff.state?.let { data.put("state", it) }
            ff.zip?.let { data.put("zip", it) }
            ff.email?.let { data.put("email", it) }
            ff.mobile?.let { data.put("mobile", it) }
            ff.designation?.let { data.put("designation", it.id) }
            ff.headQuarter?.let { data.put("headQuarter", it) }
            ff.zone?.let { data.put("zone", it) }
            ff.joiningDate?.let { data.put("joiningDate", it) }
            ff.team?.let { data.put("team", it.id) }
            ff.cfa?.let { data.put("cfa", it) }
            ff.recipientStatus?.let { data.put("status", it.id) }
            ff.statusChangeDate?.let { data.put("changedOnDate", it) }
            data.put("createdBy", user.id)
            data.put("updatedBy", user.id)
            ff.remarks?.let { data.put("remarks", it) }
            ff.emailRBM?.let { data.put("emailRBM", it) }
            ff.emailAM?.let { data.put("emailAM", it) }
            ff.businessUnit?.let { data.put("businessUnit", it.id) }

            sqlSessionTemplate.insert("FieldForceMapper.insertFieldForceHistory",data)

            errorMap["message"] = "FF updated successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)



    }


    @PostMapping("/addFieldForce")
    open fun addFieldForce(@RequestBody ff: MasterFF): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.addFieldForce(ff)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/addFieldForces")
    open fun addFieldForces(@RequestBody ff: MasterFF): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var errorMap: MutableMap<String, String> = HashMap()
        var data0: MutableMap<String, Any> = mutableMapOf()

        var recipient = mutableListOf<Recipient>()

        data0.put("code",ff.code!!)

        recipient = sqlSessionTemplate.selectList<Recipient>("FieldForceMapper.checkFieldForceCode",data0)


        var data1: MutableMap<String, Any> = mutableMapOf()

        var recipientWork = mutableListOf<Recipient>()

        data0.put("code",ff.code!!)

        recipientWork = sqlSessionTemplate.selectList<Recipient>("FieldForceMapper.checkFieldWorkId",data1)

        if(recipient.size > 0){
            errorMap["message"] = "FF Code Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)
        } else if (recipientWork.size > 0){
            errorMap["message"] = "FF WorkId Already Exist !"
            errorMap["error"] = "true"

            return ResponseEntity(errorMap , HttpStatus.BAD_REQUEST)
        }
        else{
            // insert ff
            var data: MutableMap<String, Any> = mutableMapOf()
            var recId = UUID.randomUUID().toString()

            data.put("id", recId)
            ff.name?.let { data.put("name", it.uppercase()) }
            ff.name?.let { data.put("ciName", it.lowercase()) }
            ff.code?.let { data.put("code", it) }
            ff.address?.let { data.put("address", it) }
            ff.city?.let { data.put("city", it) }
            ff.state?.let { data.put("state", it) }
            ff.zip?.let { data.put("zip", it) }
            ff.email?.let { data.put("email", it) }
            ff.mobile?.let { data.put("mobile", it) }
            ff.designation?.let { data.put("designation", it.id) }
            ff.headQuarter?.let { data.put("headQuarter", it) }
            ff.zone?.let { data.put("zone", it) }
            ff.joiningDate?.let { data.put("joiningDate", it) }
            ff.team?.let { data.put("team", it.id) }
            ff.cfa?.let { data.put("cfa", it) }
            ff.recipientStatus?.let { data.put("recipientStatus", it.id) }
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            ff.loginId?.let { data.put("loginId", it) }
            ff.gender?.let { data.put("gender", it) }
            ff.workId?.let { data.put("workId", it) }
            ff.emailRBM?.let { data.put("emailRBM", it) }
            ff.emailAM?.let { data.put("emailAM", it) }
            ff.businessUnit?.let { data.put("businessUnit", it.id) }

            sqlSessionTemplate.insert("FieldForceMapper.addFieldForce",data)


            //insert ff history

            var recpHist =  RecipientHistory()
            var repHisId =  UUID.randomUUID().toString()

            data.put("id",repHisId)
            data.put("recipientId",recId)
            ff.name?.let { data.put("name", it.uppercase()) }
            ff.name?.let { data.put("ciName", it.lowercase()) }
            ff.code?.let { data.put("code", it) }
            ff.address?.let { data.put("address", it) }
            ff.city?.let { data.put("city", it) }
            ff.state?.let { data.put("state", it) }
            ff.zip?.let { data.put("zip", it) }
            ff.email?.let { data.put("email", it) }
            ff.mobile?.let { data.put("mobile", it) }
            ff.designation?.let { data.put("designation", it.id) }
            ff.headQuarter?.let { data.put("headQuarter", it) }
            ff.zone?.let { data.put("zone", it) }
            ff.joiningDate?.let { data.put("joiningDate", it) }
            ff.team?.let { data.put("team", it.id) }
            ff.cfa?.let { data.put("cfa", it) }
            ff.recipientStatus?.let { data.put("status", it.id) }
            ff.statusChangeDate?.let { data.put("changedOnDate", it) }
            data.put("createdBy", user.id)
            data.put("updatedBy", user.id)
            ff.remarks?.let { data.put("remarks", it) }
            ff.emailRBM?.let { data.put("emailRBM", it) }
            ff.emailAM?.let { data.put("emailAM", it) }
            ff.businessUnit?.let { data.put("businessUnit", it.id) }

            sqlSessionTemplate.insert("FieldForceMapper.insertFieldForceHistory",data)

            errorMap["message"] = "FF created successfully !"
            errorMap["error"] = "false"



            return ResponseEntity(errorMap ,HttpStatus.OK)

        }
    }


    @PutMapping("/deleteUserMapping/{id}")
    fun deleteUserMapping(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = masterService.deleteUserMapping(id)
        return ResponseEntity(data, HttpStatus.OK)
    }


























}