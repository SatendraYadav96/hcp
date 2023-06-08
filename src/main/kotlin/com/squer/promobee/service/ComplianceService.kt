package com.squer.promobee.service


import com.squer.promobee.controller.dto.OptimaDataLogsDTO
import com.squer.promobee.controller.dto.OverSamplingDetaislDTO
import com.squer.promobee.controller.dto.RecipientUnblockingPartialDTO


interface ComplianceService {

    fun recipientUnblockingPartial (statusType : String, month : String,  year : String) : List<RecipientUnblockingPartialDTO>

    fun optimaMailLogs (type : String, month : String,  year : String) : List<OptimaDataLogsDTO>

    fun overSamplingDetails (month : String,  year : String) : List<OverSamplingDetaislDTO>








}