package com.squer.promobee.security.domain

class NamedSquerEntity : SquerEntity{
    var name: String?= null

    constructor(): super()
    constructor(id: String, name: String): super(id) {
        this.name = name
    }
}