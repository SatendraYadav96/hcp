package com.squer.promobee.api.v1

import com.squer.promobee.controller.NewAllocationController
import com.squer.promobee.service.NewAllocationService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/allocation")
@Tag(name = "V1 NEW ALLOCATION APIs")
@SecurityRequirement(name= "bearer-key")
@CrossOrigin
class NewAllocationApiV1(
    newAllocationService: NewAllocationService
) : NewAllocationController(
    newAllocationService = newAllocationService
)