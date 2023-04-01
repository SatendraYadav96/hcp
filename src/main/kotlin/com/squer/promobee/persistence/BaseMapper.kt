package com.squer.promobee.persistence

interface BaseMapper<T> {
    fun insert(entity: T)

    fun update(entity: T)
}
