package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class SampleMaster: AuditableEntity() {
    var name: String ?= null
    var ciName: String ?= null
    var lmid: String ?= null
    var description: String?= null
    var brandId: NamedSquerEntity? = null
    var packSize: Int ?= null
    var active: Int ?= null
    var hsnCode: String?= null
    //var cap: Int? = null


}