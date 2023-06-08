package com.squer.promobee.service

import com.squer.promobee.controller.dto.*


interface DashboardService {


    // HUB
    fun getPendingDispatch(): List<PendingDispacthDashboardDTO>

    fun getHubNearExpiry(): List<HubNearExpiryDashboardDTO>

    fun getHubPendingRevalidation(): List<HUBPendingRevalidationDashboardDTO>

    fun getHubGrnErrorLog(): List<HubGrnErrorLogDashboardDTO>

    fun getItemExpiredDetails(): List<ItemExpiredDetailsDashboardDTO>

    //BEX

    fun batchReconciliation(): List<BatchReconciliationDTO>



}