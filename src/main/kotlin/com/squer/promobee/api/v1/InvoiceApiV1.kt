package com.squer.promobee.api.v1



import com.squer.promobee.controller.InvoiceController
import com.squer.promobee.service.InvoiceService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/invoice")
@Tag(name = "V1 Invoice APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class InvoiceApiV1 (
    invoiceService: InvoiceService
)
    : InvoiceController(
    invoiceService = invoiceService
) {

}