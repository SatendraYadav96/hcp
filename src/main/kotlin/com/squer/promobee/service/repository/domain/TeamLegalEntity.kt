package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.SquerEntity

class TeamLegalEntity: SquerEntity() {
    var team: NamedSquerEntity ?= null
    var legalEntity: NamedSquerEntity ?= null
}