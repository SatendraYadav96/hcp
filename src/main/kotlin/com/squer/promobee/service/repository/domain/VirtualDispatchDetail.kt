package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class VirtualDispatchDetail: AuditableEntity() {
    var planId: DispatchPlan?= null
    var inventoryId: Inventory?= null
    var recipientId: NamedSquerEntity?= null
    var qtyDispatch: Int?= null
    var quarterlyPlanId: QuarterlyPlan?= null
    var detailStatus: NamedSquerEntity?= null
    var remarks: String?= null
    var categoryId: NamedSquerEntity?= null
}