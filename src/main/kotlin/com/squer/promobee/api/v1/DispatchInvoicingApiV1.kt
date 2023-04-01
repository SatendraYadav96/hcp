package com.squer.promobee.api.v1

import com.squer.promobee.controller.DispatchInvoicingController
import com.squer.promobee.service.DispatchInvoicingService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/dispatchInvoicing")
@Tag(name = "V1 DispatchInvoice APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class DispatchInvoicingApiV1 (
        dispatchInvoicingService: DispatchInvoicingService
): DispatchInvoicingController(
        dispatchInvoicingService = dispatchInvoicingService
) {
}