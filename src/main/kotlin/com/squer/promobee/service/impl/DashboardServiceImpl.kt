package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.DashboardService
import com.squer.promobee.service.repository.DashboardRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*


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




}