package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Vendor
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class VendorRepository @Autowired constructor(
        securityUtility: SecurityUtility
): BaseRepository<Vendor>(
        securityUtility = securityUtility
){

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getVendorByCode(code: String): List<Vendor>{
        var data : MutableMap<String, Any> = mutableMapOf()
        data.put("code", code)
        return sqlSessionFactory.openSession().selectList<Vendor>("VendorMapper.getVendorByCode", data)
    }

    fun insertVendor(vendor: Vendor){
        sqlSessionFactory.openSession().insert("VendorMapper.insertVendor", vendor)
    }

}