package com.squer.promobee.service.repository

import com.squer.promobee.mapper.UserDesignationMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.service.repository.domain.UserDesignation
import com.squer.promobee.security.util.SecurityUtility
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserDesignationRepository(securityUtility: SecurityUtility
): BaseRepository<UserDesignation>(
securityUtility= securityUtility,) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun findById(id: String): UserDesignation {
        return sqlSessionFactory.openSession().selectOne("UserDesignationMapper.findById", id)
    }
}