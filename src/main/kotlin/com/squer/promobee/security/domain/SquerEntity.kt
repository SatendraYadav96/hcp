package com.squer.promobee.security.domain

open class SquerEntity {
    lateinit var id: String

    constructor() {}

    constructor(id: String) {
        this.id = id
    }
}

open class NamedSquerId: SquerEntity{
    lateinit var name: String

    constructor(): super()

    constructor(id: String, name: String): super(id) {
        this.name = name
    }


}
