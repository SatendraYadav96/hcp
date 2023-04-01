package com.squer.promobee.validator.impl

import com.squer.promobee.validator.MapValidationErrorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult

@Service
class MapValidationErrorServiceImpl : MapValidationErrorService {
    override fun mapValidationService(result: BindingResult): ResponseEntity<*>? {
        if (result.hasErrors()) {
            val errorMap: MutableMap<String, String?> = HashMap()
            for (error in result.fieldErrors) {
                errorMap[error.field] = error.defaultMessage
            }
            return ResponseEntity<Map<String, String?>>(errorMap, HttpStatus.BAD_REQUEST)
        }
        return null
    }
}
