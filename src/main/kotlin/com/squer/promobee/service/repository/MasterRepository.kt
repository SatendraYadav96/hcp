package com.squer.promobee.service.repository


import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.*
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
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
    lateinit var sqlSessionFactory: SqlSessionFactory


    //VENDOR REPOSITORY


    fun getVendor( status: Int) : List<VendorDTO>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionFactory.openSession().selectList("VendorMapper.getVendor",data)
    }

    fun getVendorById( id: String) : Vendor {
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",id)

        return sqlSessionFactory.openSession().selectOne("VendorMapper.getVendorById",data)
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

         sqlSessionFactory.openSession().insert("VendorMapper.addVendor",data)


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

         sqlSessionFactory.openSession().update("VendorMapper.editVendor",data)


    }



    //COST CENTER REPOSITORY

    fun getCostCenter( status: Int) : List<CostCenter>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionFactory.openSession().selectList("CostCenterMapper.getCostCenter",data)
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



        sqlSessionFactory.openSession().insert("CostCenterMapper.addCostCenter",data)


        var cbr = CostCenterBrand()

        data.put("id", UUID.randomUUID().toString())
        data.put("ccmId", ccmId5)
        ccm.brandId?.let { data.put("brandId", it) }

        sqlSessionFactory.openSession().insert("CostCenterBrandMapper.addCostCenterBrand", data)



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



        sqlSessionFactory.openSession().update("CostCenterMapper.editCostCenter",data)


        var ccmId1 = ccm.id
        var cbr = CostCenterBrand()



        data.put("id", UUID.randomUUID().toString())
        data.put("ccmId", ccmId1 )
        ccm.brandId?.let { data.put("brandId", it) }

        sqlSessionFactory.openSession().insert("CostCenterBrandMapper.editCostCenterBrand",data)



    }



    //SAMPLE REPOSITORY


    fun getSample( status: Int) : List<SampleMaster>{
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("active",status)

        return sqlSessionFactory.openSession().selectList("SampleMasterMapper.getSample",data)
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
        smp.brandId?.let { data.put("brandId", it) }
        smp.packSize?.let { data.put("packSize", it) }
        smp.active?.let { data.put("active", it) }
        smp.hsnCode?.let { data.put("hsnCode", it) }
        smp.cap?.let { data.put("cap", it) }
        data.put("createdBy", user.id )
        data.put("updatedBy", user.id)




        sqlSessionFactory.openSession().insert("SampleMasterMapper.addSample",data)


    }


    fun editSample(smp: SampleMaster) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        smp.id?.let { data.put("id", it) }
        smp.lmid?.let { data.put("lmid", it.uppercase()) }
        smp.name?.let { data.put("name", it.uppercase()) }
        smp.name?.let { data.put("ciName", it.lowercase()) }
        smp.description?.let { data.put("description", it) }
        smp.brandId?.let { data.put("brandId", it) }
        smp.packSize?.let { data.put("packSize", it) }
        smp.active?.let { data.put("active", it) }
        smp.cap?.let { data.put("cap", it) }
        data.put("updatedBy", user.id)



        sqlSessionFactory.openSession().update("SampleMasterMapper.editSample",data)








    }




}


