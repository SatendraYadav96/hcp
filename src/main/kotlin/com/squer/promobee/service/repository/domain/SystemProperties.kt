package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

open class SystemProperties: AuditableEntity() {

    var name: String? = null
    var ciName: String?= null
    var value: String?= null
}