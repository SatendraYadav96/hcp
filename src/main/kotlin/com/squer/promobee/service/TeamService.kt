package com.squer.promobee.service

import com.squer.promobee.controller.dto.TeamDTO
import com.squer.promobee.service.repository.domain.Team

interface TeamService {

    fun getTeamsForUser(userId: String): List<TeamDTO>

    fun getTeam(): List<Team>

    fun getTeam(team: List<String>): List<Team>

    fun getBMTeams(userId: String): List<Team>

}