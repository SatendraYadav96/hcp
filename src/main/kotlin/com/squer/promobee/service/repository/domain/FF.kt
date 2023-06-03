package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import java.util.Date

open class FF: AuditableEntity() {
    var name: String? = null
    var ciName: String?= null
    var code: String?= null
    var address: String?= null
    var city: String?= null
    var state: String?= null
    var zip: String?= null
    var email: String?= null
    var mobile: String?= null
    var designation: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
    var headQuarter: String?= null
    var zone: String?= null
    var joiningDate: Date?= null
    var team: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
    var amName: String?= null
    var amCode: String?= null
    var rmName: String?= null
    var rmCode: String?= null
    var nsmName: String?= null
    var nsmCode: String?= null
    var cfa: String?= null
    var recipientStatus: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
    var statusChangeDate: Date?= null
    var loginId: String?= null
    var gender: String?= null
    var workId: String?= null
    var emailAM: String?= null
    var emailRBM: String?= null
    var businessUnit: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()

}