package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class ItemCategoryMaster: AuditableEntity() {
    var name: String?=  null
    var ciName: String?= null
    var cutOffBeforeDays: Int?= null
    var expireAfterDays: Int?= null
    var active: Int?= null
}