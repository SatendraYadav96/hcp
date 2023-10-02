package com.squer.promobee.api.v1

import com.squer.promobee.controller.EmailController
import com.squer.promobee.service.EmailService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/email")
@Tag(name = "V1 Email APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class EmailApiV1(
    emailService: EmailService
): EmailController(
    emailService = emailService
) {


}