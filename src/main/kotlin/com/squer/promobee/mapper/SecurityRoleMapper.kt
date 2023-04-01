package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.security.domain.SecurityRole
import org.apache.ibatis.annotations.*

@Mapper
interface SecurityRoleMapper: BaseMapper<SecurityRole> {
    @Select("SELECT * from SECURITY_ROLE where id=#{id}")
    @ResultMap("securityRoleResultMap")
    fun findById(id: String): SecurityRole?

    @Select("SELECT SECURITY_ROLE.id id, name, ci_name, created_at, updated_at, created_by, updated_by from SECURITY_ROLE inner join SECURITY_USER_ROLE on role_id = SECURITY_ROLE.id where user_id = #{userId}")
    @ResultMap("securityRoleResultMap")
    fun findByUserId(userId: String): List<SecurityRole>?
}
