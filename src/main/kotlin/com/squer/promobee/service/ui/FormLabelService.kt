package com.squer.promobee.service.ui

import com.squer.promobee.service.repository.domain.ui.FormLabelMeta

interface FormLabelService {

    fun fetchLabel(code: String): FormLabelMeta?

}
