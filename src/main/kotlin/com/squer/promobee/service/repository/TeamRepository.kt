package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.UserRoleEnum
import com.squer.promobee.controller.dto.TeamDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Team
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository

@Repository
class TeamRepository @Autowired constructor(
        securityUtility: SecurityUtility,
        @Autowired
        var newAllocationRepository: NewAllocationRepository

): BaseRepository<Team>(
        securityUtility = securityUtility

) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getTeamsForUser(userId: String): List<TeamDTO>{
        var user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var users = User()

        if(user.userDesignation!!.id == UserRoleEnum.TEAM_SUPPORT_EXECUTIVE_ID.id){
            users = newAllocationRepository.loginAsBM(user.id)
            user = users

        }else{
            user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        }
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("userId", user.id)
        return sqlSessionFactory.openSession().selectList("TeamMapper.usersTeamSelect",data)
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