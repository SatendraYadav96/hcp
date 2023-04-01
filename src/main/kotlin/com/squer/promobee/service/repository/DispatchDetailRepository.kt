package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.DispatchDetail
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository


@Repository
class DispatchDetailRepository(
        securityUtility: SecurityUtility
): BaseRepository<DispatchDetail>(
        securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun insertDispatchDetail(detail: DispatchDetail){
        sqlSessionFactory.openSession().insert("DispatchDetailMapper.dispatchDetailInsert")
    }
}