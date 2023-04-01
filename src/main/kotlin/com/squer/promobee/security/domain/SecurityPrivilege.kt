package com.squer.promobee.security.domain

import com.squer.promobee.persistence.EntityMeta

@EntityMeta(prefix="spriv", tableName = "security_privilege")
class SecurityPrivilege: java.io.Serializable, SquerEntity() {

    var name: String? = null

    var ciName: String? = null
}
