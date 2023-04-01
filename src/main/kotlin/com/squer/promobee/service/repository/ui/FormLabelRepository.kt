package com.squer.promobee.service.repository.ui

import com.squer.promobee.service.repository.domain.ui.FormLabelMeta
import org.springframework.stereotype.Repository

@Repository
class FormLabelRepository { //: JpaRepository<FormLabelMeta, Long> {

    fun getFormLabelMetaByCode(code: String): FormLabelMeta? {
        return null
    }
}
