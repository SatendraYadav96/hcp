package com.squer.promobee.service

import com.squer.promobee.controller.dto.GRNAckDTO
import com.squer.promobee.service.repository.domain.GRNAcknowledgement

interface GrnService {

    fun getUnacknowledgeData() : MutableMap<String, Any>

    fun rejectAcknowledge(grnId: String, reason: String, userId: String)

    fun approveAcknowledge(data: GRNAckDTO, userId: String)
}