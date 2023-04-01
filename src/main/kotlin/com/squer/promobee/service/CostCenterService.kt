package com.squer.promobee.service

interface CostCenterService {

    fun getCostCenterNameById(id: String?): Map<String, Any>

    fun getCostCenterCodeById(ccmId: String?): Map<String, Any>
}