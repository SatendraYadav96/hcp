package com.squer.promobee.api.v1

import com.squer.promobee.controller.ReportController
import com.squer.promobee.service.ReportService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/report")
@Tag(name = "V1 Report APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class ReportApiV1(
    reportService: ReportService
): ReportController(
    reportService = reportService
){

}