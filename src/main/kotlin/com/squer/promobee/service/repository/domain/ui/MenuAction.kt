package com.squer.promobee.service.repository.domain.ui

import com.squer.promobee.persistence.EntityMeta
import com.squer.promobee.security.domain.SecurityPrivilege
import com.squer.promobee.security.domain.SquerEntity

@EntityMeta(prefix="menua", tableName = "menu_action")
class MenuAction: java.io.Serializable, SquerEntity() {

    var uiInterface: String? = null

    var name: String? = null

    var path: String? = null

    var privilege: SecurityPrivilege? = null

    var icon: String? = null

    var parent: MenuAction? = null
}
