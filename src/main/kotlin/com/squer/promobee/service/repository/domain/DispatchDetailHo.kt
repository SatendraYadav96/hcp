package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity

class DispatchDetailHo: AuditableEntity() {
    var didId: DispatchDetail?= null
    var recipientName: String?= null
    var recipientCode: String?= null
}