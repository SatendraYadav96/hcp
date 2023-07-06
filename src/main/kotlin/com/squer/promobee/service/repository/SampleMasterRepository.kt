package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.SampleMaster
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class SampleMasterRepository @Autowired constructor(
        securityUtility: SecurityUtility
): BaseRepository<SampleMaster>(
        securityUtility = securityUtility
){

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    fun getSampleName(lmid: String): Map<String, Any>{

        return sqlSessionFactory.openSession().selectOne("SampleMasterMapper.getSampleName", lmid)
    }

    fun getSampleByLmid(lmid: String): SampleMaster{
        var data : MutableMap<String, Any> = mutableMapOf()
        data.put("lmId", lmid)
        return sqlSessionFactory.openSession().selectOne("SampleMasterMapper.getSampleByLmid")
    }
}