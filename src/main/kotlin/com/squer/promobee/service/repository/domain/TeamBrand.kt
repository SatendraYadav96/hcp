package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.SquerEntity

class TeamBrand : SquerEntity(){
    var teamId: NamedSquerEntity ?= null
    var brandId: NamedSquerEntity ?= null
}