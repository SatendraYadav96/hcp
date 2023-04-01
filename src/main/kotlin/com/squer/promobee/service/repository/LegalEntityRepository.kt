package com.squer.promobee.service.repository

import com.squer.promobee.mapper.LegalEntityMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.service.repository.domain.LegalEntity
import com.squer.promobee.security.util.SecurityUtility
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class LegalEntityRepository(securityUtility: SecurityUtility)
    : BaseRepository<LegalEntity>(securityUtility = securityUtility){

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun findById(id: String): LegalEntity {
        return sqlSessionFactory.openSession().selectOne("LegalEntityMapper.findById", id)
    }
}