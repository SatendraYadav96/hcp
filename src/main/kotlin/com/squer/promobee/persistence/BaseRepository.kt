package com.squer.promobee.persistence

import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.SquerEntity
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

open class BaseRepository<T:SquerEntity> @Autowired constructor(
    private val securityUtility: SecurityUtility,
){

    fun insert(mapper: BaseMapper<T>, entity: T): T {
        entity.id = "${getMeta(entity).prefix}${UUID.randomUUID().toString().lowercase().replace("-","")}"
        if (entity is AuditableEntity) {
            entity.createdAt = Date()
            entity.updatedAt = Date()
            entity.createdBy = (securityUtility.getLoggedInPrincipal() as User).id
            entity.updatedBy = null
        }
        mapper.insert(entity)
        return entity
    }

    private fun getMeta(entity: T): EntityMeta {
        return entity.javaClass.getAnnotation(EntityMeta::class.java)
    }
}
