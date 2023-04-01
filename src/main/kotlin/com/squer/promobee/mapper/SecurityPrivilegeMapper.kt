package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.security.domain.SecurityPrivilege
import com.squer.promobee.security.domain.SecurityRole
import org.apache.ibatis.annotations.*

@Mapper
interface SecurityPrivilegeMapper: BaseMapper<SecurityPrivilege> {

   /* @Select("SELECT SECURITY_PRIVILEGE.id id, name, ci_name from SECURITY_PRIVILEGE " +
            " inner join SECURITY_ROLE_PRIVILEGE on privilege_id = SECURITY_PRIVILEGE.id where role_id = #{roleId}")
    @ResultMap("securityRoleResultMap")
    fun findByUserId(userId: String): List<SecurityRole>? */
}
