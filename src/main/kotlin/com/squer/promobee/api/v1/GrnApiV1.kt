package com.squer.promobee.api.v1

import com.squer.promobee.controller.GRNController
import com.squer.promobee.service.GrnService
import com.squer.promobee.service.UploadLogService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/grn")
@Tag(name = "V1 GRN APIs")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin
class GrnApiV1(
        grnService: GrnService,
        uploadLofService: UploadLogService
) : GRNController(
        grnService = grnService,
        uploadLogService = uploadLofService
)