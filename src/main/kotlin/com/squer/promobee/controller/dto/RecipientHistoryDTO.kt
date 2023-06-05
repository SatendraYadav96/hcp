package com.squer.promobee.controller.dto

import com.squer.promobee.security.domain.NamedSquerEntity

class RecipientHistoryDTO {

    var  id : String? = null
    var  recipientId: String? = null
    var  name : String? = null
    var  code : String? = null
    var  team : NamedSquerEntity? = null
    var  designation : NamedSquerEntity? = null
    var  contact : String? = null
    var  address : String? = null
    var  city : String? = null
    var  state : String? = null
    var  zip : String? = null
    var  cfa : String? = null
    var  status : NamedSquerEntity? = null
    var  remarks : String? = null
    var  changedOnDate : String? = null
    var  changedBy : String? = null

}