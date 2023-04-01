package com.squer.promobee.controller.dto

import java.util.Date

class GRNAckDTO {
    var grnId: String ?= null
    var category: String ?= null
    var costCenterCode: String ?= null
    var expiryDate: Date ?= null
    var itemCode: String ?= null
    var medicalCode: String?= null
    var basePack: Int?= null
    var numBoxes: Int?= null
    var hsnCode: String?= null
    var ratePer: Int?= null
    var units: Int ?= null
}