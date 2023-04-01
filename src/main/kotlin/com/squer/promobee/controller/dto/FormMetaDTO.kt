package com.squer.promobee.controller.dto

import com.squer.promobee.service.repository.domain.ui.FormLabelMeta

class FormMetaDTO {
    var id: Long? = null
    var code: String? = null
    var title: String? = null
    var fields: Map<String, FormLabelMeta> = mutableMapOf<String, FormLabelMeta>()
}
