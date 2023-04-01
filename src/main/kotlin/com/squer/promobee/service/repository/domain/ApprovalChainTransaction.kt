package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import com.squer.promobee.security.domain.User

class ApprovalChainTransaction: AuditableEntity() {
    var typeStatus: NamedSquerEntity?= null
    var owner: String?= null
    var approvedByUser: NamedSquerEntity?= null
    var designation: NamedSquerEntity?= null
    var apiStatus: NamedSquerEntity?= null
    var comments: String?= null
}