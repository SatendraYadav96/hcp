package com.squer.promobee.security.domain

import java.util.*

open class AuditableEntity: SquerEntity() {
    var createdAt: Date? = null

    var updatedAt: Date? = null

    var createdBy: String? = null

    var updatedBy: String? =  null
}
