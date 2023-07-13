package com.squer.promobee.controller.dto

class DispatchRegisterParamDTO {
    var businessUnit:ArrayList<String> = ArrayList<String>()
    var division:ArrayList<String> = ArrayList<String>()
    var startDate: String? = null
    var endDate: String? = null
    var userId: String? = null
    var userDesgId: String? = null
    var team: ArrayList<String> = ArrayList<String>()
    var filterPlan: Int? = null
    var statusId: String? = null
}