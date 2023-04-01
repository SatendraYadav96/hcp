package com.squer.promobee.service.impl

import com.squer.promobee.service.TeamLegalEntityService
import com.squer.promobee.service.repository.TeamLegalEntityRepository
import com.squer.promobee.service.repository.domain.TeamLegalEntity
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class TeamLegalEntityServiceImpl @Autowired constructor(
        private val teamLegalEntityRepository: TeamLegalEntityRepository
): TeamLegalEntityService{

    override fun getUsersLegalTeam(entityId: String): List<TeamLegalEntity>{
        return teamLegalEntityRepository.getUsersLegalTeam(entityId)
    }
}