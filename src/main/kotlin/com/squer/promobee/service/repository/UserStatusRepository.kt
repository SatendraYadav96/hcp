package com.squer.promobee.service.repository

import com.squer.promobee.mapper.UserStatusMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.service.repository.domain.UserStatus
import com.squer.promobee.security.util.SecurityUtility
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserStatusRepository(securityUtility: SecurityUtility):
    BaseRepository<UserStatus> (securityUtility = securityUtility,){


        @Autowired
        lateinit var sqlSessionFactory: SqlSessionFactory

        fun getStatusList(): List<UserStatus>{
            return sqlSessionFactory.openSession().selectList("UserStatusMapper.getStatusList")
        }
}