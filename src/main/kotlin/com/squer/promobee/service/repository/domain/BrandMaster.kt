package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class BrandMaster: AuditableEntity() {
    var name: String?= null
    var ciName: String?= null
    var code: String?= null
    var active: Int?= null
    var division: NamedSquerEntity? = null
    var user:ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
    var team: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
    var costCenter:ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
}