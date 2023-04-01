package com.squer.promobee.controller.dto

import java.util.*

class GroupingInvoiceDetailsDTO {
    var InvoiceHeaderID: String ?= null
    var InvoiceNumber: Int ?= null
    var GroupInvoiceNumber: Int ?= null
    var Status: String ?= null
    var StatusID: String ?= null
    var InvoiceDate: Date?= null
    var RecipientID: String ?= null
    var RecipientName: String ?= null
    var RecipientCode : String ?= null
    var RecipientDesgID: String ?= null
    var RecipientDesgName: String ?= null
    var RecipientState: String ?= null
    var RecipientCity: String ?= null
    var Boxes : Int ?= null
    var Weight: Double ?= null
    var TransporterID: String ?= null
    var TransporterName: String ?= null
    var LRNumber: String ?= null
    var DispatchType:Int ?= null
    var StrDispatchType: String ?= null
    var PlanName: String ?= null
    var TeamID: String ?= null
    var TeamName: String ?= null
    var Month: Int ?= null
    var Year: Int ?= null
}