package com.squer.promobee.security.domain

import com.squer.promobee.persistence.EntityMeta

@EntityMeta(prefix = "srole", tableName = "security_role")
class SecurityRole: java.io.Serializable, AuditableEntity() {

    var ciName: String? =  null

    var privileges: Set<SecurityPrivilege>? = HashSet<SecurityPrivilege>()
}
