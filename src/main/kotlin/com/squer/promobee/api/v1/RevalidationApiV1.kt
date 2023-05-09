package com.squer.promobee.api.v1


import com.squer.promobee.controller.RevalidationController
import com.squer.promobee.service.RevalidationService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/revalidation")
@Tag(name = "V1 Revalidation APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class RevalidationApiV1(
    revalidationService: RevalidationService
): RevalidationController(
    revalidationService = revalidationService
) {


}