package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class StateCodeMaster: AuditableEntity() {

    var name: String?= null
    var ciName: String?= null
    var code: String?= null


}