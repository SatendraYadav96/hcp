package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.TeamLegalEntity

interface TeamLegalEntityService {

    fun getUsersLegalTeam(entityId: String): List<TeamLegalEntity>
}