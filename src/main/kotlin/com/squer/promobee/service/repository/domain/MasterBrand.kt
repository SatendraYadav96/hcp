package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class MasterBrand: AuditableEntity() {
    var name: String?= null
    var ciName: String?= null
    var code: String?= null
    var active: Int?= null
    var division: NamedSquerEntity? = null
    var user:ArrayList<String> = ArrayList<String>()
    var team: ArrayList<String> = ArrayList<String>()
//    var costCenter:ArrayList<String> = ArrayList<String>()
    var costCenter:String?= null
}