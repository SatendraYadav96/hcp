package com.squer.promobee.service.repository.domain.ui

import DataTypesEnum
import com.squer.promobee.persistence.EntityMeta
import com.squer.promobee.security.domain.SquerEntity


@EntityMeta(prefix = "fmmet", tableName = "form_meta")
class FormMeta: java.io.Serializable, SquerEntity() {

    var formCode: String? = null

    var code: String? = null

    var required: Boolean = false

    var minLength: Int? = null

    var maxLength: Int? = null

    var minValue: Double? = null

    var maxValue: Double? = null

    var dataType: DataTypesEnum? = null
}
