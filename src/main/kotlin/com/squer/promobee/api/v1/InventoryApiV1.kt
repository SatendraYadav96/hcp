package com.squer.promobee.api.v1


import com.squer.promobee.controller.InventoryController
import com.squer.promobee.service.InventoryService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/inventory")
@Tag(name = "V1 Inventory APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class InventoryApiV1 (
    inventoryService: InventoryService
)
    : InventoryController(
    inventoryService = inventoryService
) {

}