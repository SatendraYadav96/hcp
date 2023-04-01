package com.squer.promobee.api.v1

import com.squer.promobee.controller.UIController
import com.squer.promobee.security.jwt.JwtTokenProvider
import com.squer.promobee.service.MenuActionService
import com.squer.promobee.service.ui.FormService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/ui")
@Tag(name = "V1 UI APIs")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin
class UIApiV1(
        menuActionService: MenuActionService,
        formService: FormService, tokenProvider: JwtTokenProvider
) :
    UIController(
        menuActionService = menuActionService,
        formService=formService, tokenProvider,
    )
