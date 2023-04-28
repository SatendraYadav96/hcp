package com.squer.promobee.service.repository.domain

import com.fasterxml.jackson.databind.util.Named
import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class Doctor {

    var id: String? = null
    var code: String?= null
    var name: String?= null
    var mobile: String?= null
    var address:String?= null
    var city:String?= null
    var state:String?= null
    var zip:String?= null
    var team:String?= null
    var teamId:NamedSquerEntity?= null
    var designationId:NamedSquerEntity?= null
    var street1:String?= null
    var street2:String?= null
    var street3:String?= null


}