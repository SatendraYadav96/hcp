package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Transporter
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class TransporterRepository @Autowired constructor(
        securityUtility: SecurityUtility
) : BaseRepository<Transporter>(
        securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getTransporter(): List<Transporter>{
        return sqlSessionFactory.openSession().selectList("TransporterMapper.getTransporter")
    }
}