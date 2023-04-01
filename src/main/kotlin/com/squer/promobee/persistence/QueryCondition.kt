package com.squer.promobee.persistence

class QueryCondition(column: String, operator: String, value: Any) {
    lateinit var column: String
    var operator: String = "="
    lateinit var value: Any
}
