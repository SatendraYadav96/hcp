package com.squer.promobee.service

import com.squer.promobee.controller.dto.RecipientUnblockingPartialDTO


interface ComplianceService {

    fun recipientUnblockingPartial (statusType : String, month : String,  year : String) : List<RecipientUnblockingPartialDTO>








}