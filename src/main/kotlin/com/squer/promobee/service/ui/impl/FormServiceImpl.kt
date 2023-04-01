package com.squer.promobee.service.ui.impl

import com.squer.promobee.controller.dto.FormMetaDTO
import com.squer.promobee.service.repository.domain.ui.FormLabelMeta
import com.squer.promobee.service.repository.ui.FormLabelRepository
import com.squer.promobee.service.repository.ui.FormMetaRepository
import com.squer.promobee.service.ui.FormService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FormServiceImpl @Autowired constructor(
        private val formMetaRepository: FormMetaRepository,
        private val formLabelRepository: FormLabelRepository
): FormService {

    override fun getFormMeta(formCode: String): FormMetaDTO? {
        val formMeta = formMetaRepository.getFormMetaByFormCode(formCode)
        if (formMeta == null) return null
        var meta = FormMetaDTO()
        val labels = mutableMapOf<String, FormLabelMeta>()
        formMeta.forEach { it ->
            val label = formLabelRepository.getFormLabelMetaByCode(it.code!!)
            labels[it.code!!] = label!!
        }
        meta.fields = labels
        return meta;
    }
}
