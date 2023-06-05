package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import java.util.*

open class RecipientHistory: AuditableEntity() {
    var  recipientId: String? = null
    var  name : String? = null
    var  ciName : String? = null
    var  code : String? = null
    var  team : NamedSquerEntity? = null
    var  designation : NamedSquerEntity? = null
    var  mobile : String? = null
    var email: String?= null
    var headQuarter: String?= null
    var zone: String?= null
    var joiningDate: String?= null
    var  address : String? = null
    var  city : String? = null
    var  state : String? = null
    var  zip : String? = null
    var  cfa : String? = null
    var  status : NamedSquerEntity? = null
    var  remarks : String? = null
    var  changedOnDate : String? = null
    var  changedBy : String? = null
    var emailAM: String?= null
    var emailRBM: String?= null
    var businessUnit: NamedSquerEntity? = null


}