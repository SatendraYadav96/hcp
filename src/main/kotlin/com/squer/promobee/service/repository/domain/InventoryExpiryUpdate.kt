package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity
import java.util.Date

class InventoryExpiryUpdate: AuditableEntity() {
    var inventoryId: Inventory?= null
    var expiryDate: Date?= null
    var uploadDocumentPath: String?= null
    var ownerId: NamedSquerEntity?= null
    var statusId: NamedSquerEntity?= null
    var quantityRemaining: Int?= null
    var replyNote: String?= null
}