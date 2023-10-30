package com.squer.promobee.controller.dto


class PickingSlipDTO {
    lateinit var team : String
    lateinit var teamID: String
    lateinit var ownerId: String
    lateinit var brandManager: String
    var months: Int = 0
    var years: Int = 0
    var specialDispatch: Int = 0
    lateinit var planName: String
    lateinit var planID: String
    lateinit var planInvoiceStatus: String
    var approvalDate: String?= null
}

class PickListDetailsDTO{
    lateinit var team : String
    lateinit var teamID: String
    lateinit var ownerId: String
    lateinit var ownerName: String
    var dispatchDetailId: String?= null
    var months: Int = 0
    var years: Int = 0
    lateinit var planName: String
    lateinit var planID: String
    lateinit var planInvoiceStatus: String
    var approvalDate: String?= null
}