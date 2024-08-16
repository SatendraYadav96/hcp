package com.squer.promobee.controller.dto

class DispatchesReportDTO {
    val businessUnit:String ?= null
    val divison:String ?= null
    val recipientName:String ?= null
    val recipientCode:String ?= null
    val productCode:String ?= null
    val productName:String ?= null
    val quantity:Double ?= 0.0
    val amount:Double ?= 0.0
    val invoiceDate:String ?= null
    val teamName:String ?= null
    val desigation:String ?= null
    val inhId: String? = null
    val invoiceNo:Int ?= null
    val lRNo:String ?= null
    val courierName:String ?= null
    val docketStatus : String? = null
    var costCenter:String? = null
    var ratePerUnit:Double? = 0.0
    var hsnCode: String? = null
    var gstRate: String? = null

}
