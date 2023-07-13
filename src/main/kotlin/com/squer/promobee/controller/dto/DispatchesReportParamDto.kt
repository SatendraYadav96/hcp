package com.squer.promobee.controller.dto

class DispatchesReportParamDto {
    var businessUnit:ArrayList<String> = ArrayList<String>()
    var division:ArrayList<String> = ArrayList<String>()
    var startDate: String? = null
    var endDate: String? = null
    var userId: String? = null
    var userDesgId: String? = null
    var filter: Int? = null
    var filterPlan: Int? = null
}