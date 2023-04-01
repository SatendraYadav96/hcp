package com.squer.promobee.validator

import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult


interface MapValidationErrorService {
    fun mapValidationService(result: BindingResult): ResponseEntity<*>?
}
