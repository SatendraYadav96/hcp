package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import java.util.Date

class DetailsCompliance: AuditableEntity() {
    var territoryName: String?= null
    var territoryId: String?= null
    var personId: String?= null
    var personName: String?= null
    var locationId: String?= null
    var locationName: String?= null
    var visited: Date?= null
    var itemCategory: String?= null
    var itemId: String?= null
    var itemName: String?= null
    var batchNo: String?= null
    var quantity: Int?= null
    var subteam: String?= null
    var team: String?= null
    var am: String?= null
    var rbm: String?= null
    var month: String?= null
    var startDate: Date?= null
    var endDate: Date?= null
}