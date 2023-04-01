package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class InvoiceDetail : AuditableEntity(){
    var headerId: InvoiceHeader?= null
    var itemId: NamedSquerEntity?= null
    var quantity: Double?= null
    var didId: DispatchDetail?= null
    var value: Double?= null
    var inventoryId: Inventory?= null
    var hsnCode: String?= null
    var rate: String?= null
}