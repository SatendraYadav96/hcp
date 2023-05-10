package com.squer.promobee.api.v1


import com.squer.promobee.controller.DashboardController
import com.squer.promobee.service.DashboardService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/dashboard")
@Tag(name = "V1 Dashboard APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class DashboardApiV1(
    dashboardService: DashboardService
): DashboardController(
    dashboardService = dashboardService
) {


}