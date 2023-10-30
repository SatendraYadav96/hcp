package com.squer.promobee.service.repository


import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.*
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Repository
class MasterRepository
    (
    securityUtility: SecurityUtility
): BaseRepository<BU>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionTemplate: SqlSessionTemplate



    fun getVendor( status: Int) : List<VendorDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionTemplate.selectList("VendorMapper.getVendor",data)
    }

    fun getVendorById( id: String) : Vendor {
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)

        return sqlSessionTemplate.selectOne("VendorMapper.getVendorById",data)
    }


    fun addVendor(vnd: Vendor): Map<String, Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data0: MutableMap<String, Any> = mutableMapOf()

        lateinit var jsonResult : Map<String, Any>

        var vendorCode = 0;
        vnd.code?.let { data0.put("code", it) }

        vendorCode = sqlSessionTemplate.selectOne("VendorMapper.vendorExist",data0)

        if(vendorCode > 0 ){
            println("Vendor Already exist in database.")
            jsonResult = mapOf("success" to false, "message" to "Vendor Code Already Exist !")

            return jsonResult
        }
        else {

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

            jsonResult = mapOf("success" to true, "message" to "Vendor created successfully !")

            return jsonResult

        }

        return jsonResult

    }


    fun editVendor(vnd: Vendor) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

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


    }



    //COST CENTER REPOSITORY

    fun getCostCenter( status: Int) : List<CostCenter>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionTemplate.selectList("CostCenterMapper.getCostCenter",data)
    }


    fun addCostCenter(ccm: MasterCostCenter): Map<String, Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        lateinit var jsonResult : Map<String, Any>

        var costCenter = CostCenter()

        var data0 : MutableMap<String, Any> = mutableMapOf()

        data0.put("code", ccm.code!!)

        costCenter = sqlSessionTemplate.selectOne("CostCenterMapper.checkCostCenterCode",data0)


        if(costCenter.code == ccm.code){
            jsonResult = mapOf("success" to false, "message" to "Cost Center Code Already Exist !")

            return jsonResult
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

            jsonResult = mapOf("success" to true, "message" to "Cost Center created successfully !")

            return jsonResult
        }


        return jsonResult



    }



    fun editCostCenter(ccm: MasterCostCenter) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
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



    }


    fun getCostCenterById( id: String) : CostCenter {
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)

         return sqlSessionTemplate.selectOne("CostCenterMapper.getCostCenterById",data)
    }



    //SAMPLE REPOSITORY


    fun getSample( status: Int) : List<SampleMaster>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionTemplate.selectList("SampleMasterMapper.getSample",data)
    }


    fun addSample(smp: SampleMaster): Map<String, Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        lateinit var jsonResult : Map<String, Any>

        var sample = SampleMaster()

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("lmid",smp.lmid!!)

        sample = sqlSessionTemplate.selectOne("SampleMasterMapper.getSampleByLmid",data0)

        if(sample.lmid == smp.lmid){
            jsonResult = mapOf("success" to false, "message" to "Sample Code Already Exist !")

            return jsonResult
        } else {
            var data: MutableMap<String, Any> = mutableMapOf()
            var smp1 = UUID.randomUUID().toString()

            data.put("id",smp1 )
            smp.lmid?.let { data.put("lmid", it.uppercase()) }
            smp.name?.let { data.put("name", it.uppercase()) }
            smp.name?.let { data.put("ciName", it.lowercase()) }
            smp.description?.let { data.put("description", it) }
            data.put("brandId",NamedSquerEntity(smp.brandId?.id.toString(),""))
            smp.packSize?.let { data.put("packSize", it) }
            smp.active?.let { data.put("active", it) }
            smp.hsnCode?.let { data.put("hsnCode", it) }
            smp.cap?.let { data.put("cap", it) }
            data.put("createdBy", user.id )
            data.put("updatedBy", user.id)


            sqlSessionTemplate.insert("SampleMasterMapper.addSample",data)


            jsonResult = mapOf("success" to true, "message" to "Sample created successfully !")

            return jsonResult
        }


        return jsonResult

    }


    fun editSample(smp: SampleMaster) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

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


    }


    fun getSampleById( id: String) : SampleMaster {
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)

        return sqlSessionTemplate.selectOne("SampleMasterMapper.getSampleById",data)
    }

    fun getBusinessUnitDropdown( ) : List<BU>{
//        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionTemplate.selectList("BUMapper.getBusinessUnitDropdown")
    }


    fun getBrandDropdown( ) : List<BrandMaster>{
//        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionTemplate.selectList("BrandMasterMapper.getBrandDropdown")
    }


    fun getDivisionDropdown( ) : List<Division>{
//        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionTemplate.selectList("DivisionMapper.getDivisionDropdown")
    }

    fun getTeamDropdown( ) : List<Team>{

        return sqlSessionTemplate.selectList("TeamMapper.getTeamDropdown")
    }

    fun getCostCenterDropdown( ) : List<CostCenter>{

        return sqlSessionTemplate.selectList("CostCenterMapper.getCostCenterDropdown")
    }

    fun getItemCodeDropdown( ) : List<ItemDrodownDTO>{

        return sqlSessionTemplate.selectList("ItemMapper.getItemCodeDropdown")
    }

    fun getRecipientDropdown( ) : List<RecipientDropDownDTO>{

        return sqlSessionTemplate.selectList("RecipientMapper.getRecipientDropdown")
    }

    fun getInvoiceDropdown( ) : List<InvoiceDropdownDTO>{

        return sqlSessionTemplate.selectList("InvoiceHeaderMapper.getInvoiceDropdown")
    }


    fun getTransporter( ): List<TransporterDropdownDTO> {

        return sqlSessionTemplate.selectList("InvoiceHeaderMapper.getTransporter")
    }

    fun getLegalEntityDropdown( ): List<LegalEntity> {

        return sqlSessionTemplate.selectList("LegalEntityMapper.getLegalEntity")
    }

    fun getRecipientDesignationDropdown( ): List<RecipientDesignationDropdownDTO> {

        return sqlSessionTemplate.selectList("RecipientMapper.getRecipientDesignationDropdown")
    }

    fun getUserDesignationDropdown( ): List<UserDesignationDropdownDTO> {

        return sqlSessionTemplate.selectList("UserMapper.getUserDesignationDropdown")
    }

    fun getUserDropdown( ): List<UserDropdownDTO> {

        return sqlSessionTemplate.selectList("UserMapper.getUserDropdown")
    }


    fun getApproverDropdown( ): List<ApproverDropdownDTO> {

        return sqlSessionTemplate.selectList("UsersMasterMapper.getApproverDropdown")
    }

    fun getOwnerDropdown( ): List<ApproverDropdownDTO> {

        return sqlSessionTemplate.selectList("UsersMasterMapper.getOwnerDropdown")
    }

    fun getTseDropdown( ): List<ApproverDropdownDTO> {

        return sqlSessionTemplate.selectList("UsersMasterMapper.getTseDropdown")
    }


    //BU REPOSITORY

    fun getBusinessUnit( status: Int) : List<BU>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionTemplate.selectList("BUMapper.getBusinessUnit",data)
    }

    fun getBusinessUnitById( id: String) : BU {
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)

        return sqlSessionTemplate.selectOne("BUMapper.getBusinessUnitById",data)
    }


    fun editBusinessUnit(bu: BU) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("id", bu.id)
        bu.name?.let { data.put("name", it.uppercase()) }
        bu.name?.let { data.put("ciName", it.lowercase()) }
        bu.active?.let { data.put("active", it) }
        data.put("updatedBy",user.id)




        sqlSessionTemplate.update("BUMapper.editBusinessUnit",data)


    }


    fun addBusinessUnit(bu: BU): Map<String, Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        lateinit var jsonResult : Map<String, Any>

        var businessUnit = BU()

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("code",bu.code!!)

        businessUnit = sqlSessionTemplate.selectOne("BUMapper.checkBUCode",data0)

        if(businessUnit.code == bu.code){
            jsonResult = mapOf("success" to false, "message" to "Business Unit Code Already Exist !")

            return jsonResult
        } else {
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

            jsonResult = mapOf("success" to true, "message" to "Business Unit created successfully !")

            return jsonResult
        }

        return jsonResult


    }



    // TEAM REPOSITORY

    fun getTeam( status: Int) : List<Team>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionTemplate.selectList("TeamMapper.getTeams",data)
    }


    fun getBrandByTeamId(teamId: String):MutableList<TeamBrand>{
        var data: MutableMap<String,Any> = mutableMapOf()
        data.put("id",teamId)

        return sqlSessionTemplate.selectList<TeamBrand>("TeamBrandMapper.getBrandByTeamId",data).toMutableList()
    }

    fun getTeamById(id: String):MutableList<Team>{
        var data: MutableMap<String,Any> = mutableMapOf()
        data.put("id",id)

        return sqlSessionTemplate.selectList<Team>("TeamMapper.getTeamById",data)
    }


    fun editTeam(tem: MasterTeam) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
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


        println("Team Updated Successfully !")



    }





    fun addTeam(tem: MasterTeam): Map<String, Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        lateinit var jsonResult : Map<String, Any>

        var team = Team()

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("code",tem.code!!)

        team = sqlSessionTemplate.selectOne("TeamMapper.checkTeamCode",data0)

        if(team.code == tem.code){
            jsonResult = mapOf("success" to false, "message" to "Team Code Already Exist !")

            return jsonResult
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

            jsonResult = mapOf("success" to true, "message" to "Team created successfully !")

            return jsonResult
        }


        return jsonResult


    }


    //USER REPOSITORY

    fun getUser( status: String) : List<Users>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("userStatus",status)

        return sqlSessionTemplate.selectList("UsersMasterMapper.getUser",data)
    }

    fun getUserById(id: String):Users{
        var data: MutableMap<String,Any> = mutableMapOf()
        data.put("id",id)

        return sqlSessionTemplate.selectOne("UsersMasterMapper.getUserById",data)
    }



    fun editUser(usr: MasterUsers) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
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



    }




    fun addUser(usr: MasterUsers) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()




        // add user

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



    }



    //BRAND REPOSITORY

    fun getBrand( status: Int) : List<BrandMaster>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionTemplate.selectList("BrandMasterMapper.getBrand",data)
    }


    fun getBrandById(id: String):BrandMaster{
        var data: MutableMap<String,Any> = mutableMapOf()
        data.put("id",id)

        return sqlSessionTemplate.selectOne("BrandMasterMapper.getBrandById",data)
    }





    fun editBrand(brd: MasterBrand)  {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
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



    }



    fun addBrand(brd: MasterBrand): Map<String, Any> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data0: MutableMap<String, Any> = mutableMapOf()

        lateinit var jsonResult : Map<String, Any>

        var brand = BrandMaster()

        data0.put("code",brd.code!!)

        brand = sqlSessionTemplate.selectOne("BrandMasterMapper.checkBrandCode",data0)

        if(brand.code == brd.code){
            jsonResult = mapOf("success" to false, "message" to "Brand Code Already Exist !")

            return jsonResult
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

            jsonResult = mapOf("success" to true, "message" to "Brand created successfully !")

            return jsonResult
        }


        return jsonResult



    }



    //RECIPIENT REPOSITORY

    fun getFieldForce( ff : FieldForceDTO) : List<FF> {
        var data: MutableMap<String, Any> = mutableMapOf()

        ff.status?.let { data.put("recipientStatus", it) }
        ff.ffCode?.let { data.put("code", it) }
        ff.ffName?.let { data.put("name", it) }

        var rec =  FF()



        if((ff.ffCode == "" ) && (ff.ffName == "" )) {

            ff.status?.let { data.put("recipientStatus", it) }

            rec =  return sqlSessionTemplate.selectList<FF>("FieldForceMapper.getFieldForceByStatus",data)

        }

        else if((ff.status !=  "" ) && (ff.ffCode != "")  ){

            ff.ffCode?.let { data.put("code", it) }

            rec =  return sqlSessionTemplate.selectList<FF>("FieldForceMapper.getFieldForceByCode",data)



        }

        else if((ff.status !=  "" ) && (ff.ffName != "")){

            data.put("name", ff.ffName!!)

            rec = return sqlSessionTemplate.selectList<FF>("FieldForceMapper.getFieldForceByName",data)


        }

        return listOf(rec)


    }


    fun getFieldForceHistory(id: String):List<RecipientHistoryDTO>{
        var data: MutableMap<String,Any> = mutableMapOf()
        data.put("id",id)

        return sqlSessionTemplate.selectList<RecipientHistoryDTO>("FieldForceMapper.getFieldForceHistory",data)
    }

    fun getFieldForceById(id: String):FF{
        var data: MutableMap<String,Any> = mutableMapOf()
        data.put("id",id)

        return sqlSessionTemplate.selectOne("FieldForceMapper.getFieldForceById",data)
    }

    fun editFieldForce(ff: MasterFF) {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
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

    }







    fun addFieldForce(ff: MasterFF): Map<String, Any> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data0: MutableMap<String, Any> = mutableMapOf()

        lateinit var jsonResult : Map<String, Any>

        var recipient = Recipient()

        data0.put("code",ff.code!!)

        recipient = sqlSessionTemplate.selectOne("FieldForceMapper.checkFieldForceCode",data0)

        if(recipient.code == ff.code){
            jsonResult = mapOf("success" to false, "message" to "Recipient Code Already Exist !")

            return jsonResult
        } else{
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

            jsonResult = mapOf("success" to true, "message" to "FF created successfully !")

            return jsonResult

        }

        return jsonResult

    }


    fun deleteUserMapping(id: String) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)

        sqlSessionTemplate.delete("deleteUserBrand",data)

        sqlSessionTemplate.delete("deleteUserTeam",data)
    }





}


