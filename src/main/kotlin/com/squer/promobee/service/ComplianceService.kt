package com.squer.promobee.service


import com.squer.promobee.controller.dto.*


interface ComplianceService {

    fun recipientUnblockingPartial (statusType : String, month : String,  year : String) : List<RecipientUnblockingPartialDTO>

    fun optimaMailLogs (type : String, month : String,  year : String) : List<OptimaDataLogsDTO>

    fun overSamplingDetails (month : String,  year : String) : List<ComplianceListCrudDTO>

    fun masterBlockedList (year : String) : List<RecipientBlockedListCrudDTO>

    fun saveMasterBlockedRecipient (blockRecp : List<SaveRecipientBlockedmasterDTO>)

    fun saveOverSampling (comp :List<SaveOverSamplingDTO>)

    fun saveNonComplianceAdminRemark (nonComp :List<SaveNonComplianceAdminRemarkDTO> )







}