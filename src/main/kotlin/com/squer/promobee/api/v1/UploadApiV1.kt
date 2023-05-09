package com.squer.promobee.api.v1

import com.squer.promobee.controller.UploadController
import com.squer.promobee.service.UploadService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/upload")
@Tag(name = "V1 Upload APIs")
@SecurityRequirement(name="bearer-key")
@CrossOrigin
class UploadApiV1(
    uploadService: UploadService
): UploadController(
    uploadService = uploadService
) {


}