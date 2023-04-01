package com.squer.promobee.api.v1

import com.squer.promobee.controller.AllocationController
import com.squer.promobee.service.AllocationService
import com.squer.promobee.service.DispatchPlanService
import com.squer.promobee.service.InventoryService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/allocations")
@Tag(name = "V1 ALLOCATION APIs")
@SecurityRequirement(name= "bearer-key")
@CrossOrigin
class AllocationApiV1(
        allocationService: AllocationService
) : AllocationController(
       allocationService = allocationService
)