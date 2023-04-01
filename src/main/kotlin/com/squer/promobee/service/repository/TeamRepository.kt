package com.squer.promobee.service.repository

import com.squer.promobee.controller.dto.TeamDTO
import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Team
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class TeamRepository @Autowired constructor(
        securityUtility: SecurityUtility
): BaseRepository<Team>(
        securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getTeamsForUser(userId: String): List<TeamDTO>{
        return sqlSessionFactory.openSession().selectList("TeamMapper.usersTeamSelect",userId)
    }

    fun getTeam(): List<Team>{
        return sqlSessionFactory.openSession().selectList("TeamMapper.getTeam")
    }
    fun getTeam(team: List<String>): List<Team>{
        return sqlSessionFactory.openSession().selectList("TeamMapper.getTeam", team)
    }
    fun getBMTeams(userId: String): List<Team>{
        return sqlSessionFactory.openSession().selectList("TeamMapper.getBMTeams", userId)
    }
}