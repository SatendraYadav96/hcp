package com.squer.promobee.security.repository

import com.squer.promobee.mapper.SecurityRoleMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.SecurityRole
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class SecurityRoleRepository(
    securityUtility: SecurityUtility
): BaseRepository<User>(
    securityUtility= securityUtility,
) {


    fun findByUser(userId: String): List<SecurityRole>? {
        //return userRoleMapper.findByUserId(userId)
        //TODO
        return emptyList()
    }
}
