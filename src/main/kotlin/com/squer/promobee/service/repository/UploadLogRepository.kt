package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.UploadLog
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UploadLogRepository @Autowired constructor(
        securityUtility: SecurityUtility
): BaseRepository<UploadLog>(
        securityUtility = securityUtility
){

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getUploadLogByStatusId(statusId: String): List<UploadLog>{
        return sqlSessionFactory.openSession().selectList("UploadLogMapper.uploadLogSelectByStatusId", statusId)
    }
}