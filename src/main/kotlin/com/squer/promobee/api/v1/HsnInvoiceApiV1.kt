package com.squer.promobee.api.v1

import com.squer.promobee.controller.HsnInvoiceController

import com.squer.promobee.service.HsnInvoiceService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/hsn")
@Tag(name = "V1 Hsn APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class HsnInvoiceApiV1(
    hsnInvoiceService: HsnInvoiceService
): HsnInvoiceController(
    hsnInvoiceService = hsnInvoiceService
) {


}