package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

open class Team: AuditableEntity() {
    var name: String?= null
    var ciName: String?= null
    var code: String?= null
    var active: Int?= null
    var division: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
    var brand: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
    var ety: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()


}