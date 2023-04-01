package com.squer.promobee.api.v1

import com.squer.promobee.controller.RecipientController
import com.squer.promobee.service.RecipientService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/recipient")
@Tag(name = "V1 RECIPIENT APIs")
@SecurityRequirement(name= "bearer-key")
@CrossOrigin
class RecipientApiV1(
       recipientService: RecipientService
): RecipientController(
        recipientService = recipientService
) {
}