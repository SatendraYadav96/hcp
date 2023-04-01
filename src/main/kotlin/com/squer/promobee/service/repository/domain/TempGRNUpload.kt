package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.NamedSquerEntity
import java.util.Date
import java.util.StringJoiner

class TempGRNUpload {
    var id: String?= null
    var uploadId: UploadLog?= null
    var poNo: String?= null
    var ccmCode: String?= null
    var ccmId: NamedSquerEntity?= null
    var material: String?= null
    var batchNo:String?= null
    var materialDescription: String?= null
    var postingDate: Date?= null
    var qtyIn: Double?= null
    var amount: Double?= null
    var vendorCode: String?= null
    var vendorName: String?= null
    var vendorId: NamedSquerEntity?= null
    var ratePerUnit: Double?= null
    var medicalCode: String?= null
    var matDoc: String?= null
    var itemName: String?= null
    var sampleExpiry: Date?= null
    var errorText: String?= null
    var hsnCode: String?= null
}