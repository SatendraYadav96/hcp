package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import java.util.Date

class UploadLog: AuditableEntity() {
    var type: NamedSquerEntity?= null
    var startTime: String?= null
    var endTime: String?= null
    var totalRecord: Int?= null
    var recordUpload: Int?= null
    var statusId: NamedSquerEntity?= null
    var parentId: String?= null
}