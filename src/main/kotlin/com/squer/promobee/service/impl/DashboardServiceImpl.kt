package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.DashboardService
import com.squer.promobee.service.repository.DashboardRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@Slf4j
class DashboardServiceImpl @Autowired constructor(
    private val dashboardRepository: DashboardRepository


): DashboardService {


    private val log = LoggerFactory.getLogger(javaClass)

    //HUB

    override fun getPendingDispatch (): List<PendingDispacthDashboardDTO> {
        return dashboardRepository.getPendingDispatch()
    }

    override fun getHubNearExpiry(): List<HubNearExpiryDashboardDTO> {
        return dashboardRepository.getHubNearExpiry()
    }

    override fun getHubPendingRevalidation(): List<HUBPendingRevalidationDashboardDTO> {
        return dashboardRepository.getHubPendingRevalidation()
    }

    override fun getHubGrnErrorLog(): List<HubGrnErrorLogDashboardDTO> {
        return dashboardRepository.getHubGrnErrorLog()
    }

    override fun getItemExpiredDetails(): List<ItemExpiredDetailsDashboardDTO> {
        return dashboardRepository.getItemExpiredDetails()
    }

    //BEX

    override fun batchReconciliation(): List<BatchReconciliationDTO> {
        return dashboardRepository.batchReconciliation()
    }

    override fun bexManagementDashboard(month: Int, year: Int, toMonth: Int, toYear: Int, type: String): Any? {
        return dashboardRepository.bexManagementDashboard(month,year,toMonth,toYear,type)
    }




}