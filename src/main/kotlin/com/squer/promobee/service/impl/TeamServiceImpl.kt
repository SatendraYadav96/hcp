package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.TeamDTO
import com.squer.promobee.service.TeamService
import com.squer.promobee.service.repository.TeamRepository
import com.squer.promobee.service.repository.domain.Team
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class TeamServiceImpl @Autowired constructor(
        private val teamRepository: TeamRepository
): TeamService{

    override fun getTeamsForUser(userId: String): List<TeamDTO>{
        return teamRepository.getTeamsForUser(userId)
    }

    override fun getTeam(): List<Team>{
        return teamRepository.getTeam()
    }

    override fun getTeam(team: List<String>): List<Team>{
        return teamRepository.getTeam(team)
    }

    override fun getBMTeams(userId: String): List<Team>{
        return teamRepository.getBMTeams(userId)
    }
}