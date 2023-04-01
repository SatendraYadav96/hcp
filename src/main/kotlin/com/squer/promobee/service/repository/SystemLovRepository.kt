package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.persistence.SearchCriteria
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.SystemLov
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class SystemLovRepository(
        securityUtility: SecurityUtility
): BaseRepository<SystemLov>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getSystemLov(type: String): List<SystemLov>{
        return sqlSessionFactory.openSession().selectList("SystemLovMapper.systemLovSelect",type)
    }
}