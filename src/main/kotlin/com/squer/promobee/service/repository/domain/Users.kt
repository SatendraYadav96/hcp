package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

open class Users: AuditableEntity() {
    var name: String?= null
    var ciName: String?= null
    var username: String?= null
    var employeeCode: String?= null
    var activeFrom: String?= null
    var activeTo: String?= null
    var email: String?= null
    var approver: String?= null
    var lastLoggedIn: String?= null
    var userRecipientId: String?= null
    var userDesignation: NamedSquerEntity? = null
    var userStatus: NamedSquerEntity?= null
    var legalEntity: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()
    var appBu: NamedSquerEntity? = null
    var brand: ArrayList<NamedSquerEntity> = ArrayList<NamedSquerEntity>()


}
