package com.squer.promobee.api.v1

import com.squer.promobee.controller.MasterController
import com.squer.promobee.service.MasterService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/master")
@Tag(name = "V1 Master APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class MasterApiV1 (
    masterService: MasterService
)
    : MasterController(
    masterService = masterService
) {

}