package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class MasterCostCenter: AuditableEntity() {
    var name: String?= null
    var ciName: String?= null
    var code: String?= null
    var active: Int?= null
    var brandId: ArrayList<String> = ArrayList<String>()


}