package com.squer.promobee.persistence

@Retention(AnnotationRetention.RUNTIME)
annotation class EntityMeta(val prefix: String, val tableName: String)
