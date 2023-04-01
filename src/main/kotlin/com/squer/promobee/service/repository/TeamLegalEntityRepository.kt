package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.TeamLegalEntity
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class TeamLegalEntityRepository @Autowired constructor(
        securityUtility: SecurityUtility
): BaseRepository<TeamLegalEntity>(
        securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getUsersLegalTeam(entityId: String): List<TeamLegalEntity>{
        return sqlSessionFactory.openSession().selectList("TeamLegalEntityMapper.getUserLegalTeam", entityId)
    }
}