package com.squer.promobee.api.v1



import com.squer.promobee.controller.ComplianceController
import com.squer.promobee.service.ComplianceService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/compliance")
@Tag(name = "V1 Compliance APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class ComplianceApiV1 (
    complianceService: ComplianceService
)
    : ComplianceController(
    complianceService = complianceService
) {



}