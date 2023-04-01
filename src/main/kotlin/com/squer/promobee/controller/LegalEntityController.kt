package com.squer.promobee.controller

import com.squer.promobee.service.LegalEntityService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
open class LegalEntityController @Autowired constructor(
        private val legalEntityService: LegalEntityService
){
}