package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.Recipient

interface RecipientService {

    fun getRecipients(teamId:String): List<MutableMap<String, Any>>

}