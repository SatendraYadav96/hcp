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

    fun bexManagementDashboard( month:Int,  year:Int,  toMonth:Int,  toYear: Int,  type:String): Any?

    fun dispatchesMonthWise():List<DashboardDTO>

    fun specialCourierCostMonthWise():List<DashboardDTO>


}