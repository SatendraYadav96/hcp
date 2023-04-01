package com.squer.promobee.service.ui.impl

import com.squer.promobee.service.repository.domain.ui.FormLabelMeta
import com.squer.promobee.service.repository.ui.FormLabelRepository
import com.squer.promobee.service.ui.FormLabelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
open class FormLabelServiceImpl @Autowired constructor(
    private val formLabelRepository: FormLabelRepository
): FormLabelService {


    @Cacheable(cacheNames = ["labelStore"], key = "#code")
    override fun fetchLabel(code: String): FormLabelMeta? {
        return formLabelRepository.getFormLabelMetaByCode(code)
    }
}
