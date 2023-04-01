package com.squer.promobee.service.repository.domain

import com.fasterxml.jackson.databind.util.Named
import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class InvoiceHeader: AuditableEntity() {
    var invoiceNo: Int?= null
    var type: NamedSquerEntity?= null
    var statusId: NamedSquerEntity?= null
    var teamId: NamedSquerEntity?= null
    var recipientId: NamedSquerEntity?= null
    var addressLine1: String?= null
    var addressLine2: String?= null
    var states: String?= null
    var city: String?= null
    var zip: String?= null
    var phone: String?= null
    var weight: Double?= null
    var noOfBoxes: Int?= null
    var notes: String?= null
    var transporterId: NamedSquerEntity?= null
    var sampleValue: Double?= null
    var otherItemValue: Double?= null
    var indirectedId: String?= null
    var groupInvoiceNo: Int?= null
    var groupInvoiceId: String?= null
    var lrNumber: String?= null
    var designationId: NamedSquerEntity?= null
    var cfa: String?= null
}