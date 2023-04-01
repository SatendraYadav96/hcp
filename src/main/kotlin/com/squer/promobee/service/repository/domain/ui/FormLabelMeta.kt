package com.squer.promobee.service.repository.domain.ui

import com.squer.promobee.persistence.EntityMeta
import com.squer.promobee.security.domain.SquerEntity

@EntityMeta(prefix = "fmlbl", tableName = "form_label_meta")
class FormLabelMeta: java.io.Serializable, SquerEntity() {

    var code: String? = null

    var defaultValue: String? = null

    var customValue: String? = null

}
