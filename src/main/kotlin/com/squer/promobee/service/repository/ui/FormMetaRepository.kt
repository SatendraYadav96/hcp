package com.squer.promobee.service.repository.ui

import com.squer.promobee.service.repository.domain.ui.FormMeta
import org.springframework.stereotype.Repository

@Repository
class FormMetaRepository { //: JpaRepository<FormMeta, Long> {

    fun getFormMetaByFormCode(code: String): List<FormMeta>? {
        return null
    }
}
