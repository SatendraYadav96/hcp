package com.squer.promobee.controller.dto

import com.squer.promobee.service.repository.domain.Team

class AllocationDistributionDTO {
    var teams : List<TeamDTO> ?= null
    var allocations: List<Map<String, Any>>?= null
}

class TeamDTO: Team() {
    var recipient: Int?= 0
}