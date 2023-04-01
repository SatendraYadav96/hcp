package com.squer.promobee.service.ui

import com.squer.promobee.controller.dto.FormMetaDTO

interface FormService {

    fun getFormMeta(formCode: String): FormMetaDTO?
}
