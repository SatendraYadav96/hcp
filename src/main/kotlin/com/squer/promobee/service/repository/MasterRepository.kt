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


    fun addVendor(vnd: Vendor) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

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
        data.put("createdBy", user.id )
        data.put("updatedBy", user.id)

         sqlSessionTemplate.insert("VendorMapper.addVendor",data)


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


    fun addCostCenter(ccm: CostCenter) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()



        var ccmId5 = UUID.randomUUID().toString()

        data.put("id",ccmId5 )
        ccm.name?.let { data.put("name", it.uppercase()) }
        ccm.name?.let { data.put("ciName", it.lowercase()) }
        ccm.code?.let { data.put("code", it.uppercase()) }
        ccm.active?.let { data.put("active", it) }
        data.put("createdBy", user.id )
        data.put("updatedBy", user.id)



        sqlSessionTemplate.insert("CostCenterMapper.addCostCenter",data)




        var cbr = CostCenterBrand()

        data.put("id", UUID.randomUUID().toString())
        data.put("ccmId", ccmId5)
        data.put("brandId",NamedSquerEntity(ccm.brandId?.id.toString(),""))

        sqlSessionTemplate.insert("CostCenterBrandMapper.addCostCenterBrand", data)



    }



    fun editCostCenter(ccm: CostCenter) {
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
        var cbr = CostCenterBrand()

        data.put("id", UUID.randomUUID().toString())
        ccmId2?.let { data.put("ccmId", it) }
//        data.put("brandId",NamedSquerEntity(ccm.brandId?.id.toString(),""))
       // ccm.brandId?.let { data.put("brandId", it) }
        data.put("brandId",NamedSquerEntity(ccm.brandId?.id.toString(),""))

        sqlSessionTemplate.insert("CostCenterBrandMapper.editCostCenterBrand",data)



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


    fun addSample(smp: SampleMaster) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

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


    fun addBusinessUnit(bu: BU) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

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





    fun addTeam(tem: MasterTeam) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()




        // add team

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



    }


    //USER REPOSITORY

    fun getUser( status: String) : List<Users>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("userStatus",status)

        return sqlSessionTemplate.selectList("UsersMasterMapper.getUser",data)
    }

    fun getUserById(id: String):MutableList<Users>{
        var data: MutableMap<String,Any> = mutableMapOf()
        data.put("id",id)

        return sqlSessionTemplate.selectList<Users>("UsersMasterMapper.getUserById",data)
    }



    fun editUser(usr: MasterUsers) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()




        // update user

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

















}


