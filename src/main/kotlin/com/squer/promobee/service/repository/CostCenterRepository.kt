package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.CostCenter
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class CostCenterRepository @Autowired constructor(
        securityUtility: SecurityUtility
): BaseRepository<CostCenter>(
        securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory



    fun getCostCenterNameById(id: String?): Map<String, Any>{
        return sqlSessionFactory.openSession().selectOne("CostCenterMapper.getCostCenterNameById", id)
    }

    fun getCostCenterCodeById(ccmId: String?): Map<String, Any>{
        return sqlSessionFactory.openSession().selectOne("costCenterResultMap.getCostCenterCodeById", ccmId       )
    }
}