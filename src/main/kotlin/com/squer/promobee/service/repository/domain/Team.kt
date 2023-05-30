package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

open class Team: AuditableEntity() {
    var name: String?= null
    var ciName: String?= null
    var code: String?= null
    var active: Int?= null
    var division: NamedSquerEntity?= null
    var brand: NamedSquerEntity?= null
    var ety: NamedSquerEntity?= null
//    var division: List<NamedSquerEntity?>?= null

}