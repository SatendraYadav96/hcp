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



    fun editCostCenter(ccm: CostCenterDTO) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        ccm.ccmId?.let { data.put("id", it) }
        ccm.ccmName?.let { data.put("name", it.uppercase()) }
        ccm.ccmName?.let { data.put("ciName", it.lowercase()) }
        ccm.ccmCode?.let { data.put("code", it.uppercase()) }
        ccm.ccmActive?.let { data.put("active", it) }
        data.put("updatedBy", user.id)

        sqlSessionTemplate.update("CostCenterMapper.editCostCenter",data)
        var ccmId2 = ccm.ccmId
        var cbr = CostCenterBrand()

        data.put("id", UUID.randomUUID().toString())
        ccmId2?.let { data.put("ccmId", it) }
//        data.put("brandId",NamedSquerEntity(ccm.brandId?.id.toString(),""))
        ccm.brandId?.let { data.put("brandId", it) }

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











}


