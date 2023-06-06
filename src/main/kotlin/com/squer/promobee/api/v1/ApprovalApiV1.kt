package com.squer.promobee.api.v1


import com.squer.promobee.controller.ApprovalController
import com.squer.promobee.service.ApprovalService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/approval")
@Tag(name = "V1 Approval APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class ApprovalApiV1 (
    approvalService: ApprovalService
)
    : ApprovalController(
    approvalService = approvalService
) {



}