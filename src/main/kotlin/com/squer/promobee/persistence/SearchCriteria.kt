package com.squer.promobee.persistence

class SearchCriteria() {
    var conditions = mutableListOf<QueryCondition>()

    fun addCondition(column: String, value: Any) {
        conditions.add(QueryCondition(column, "=", value))
    }

    fun addCondition(column: String, operator: String, value: Any) {
        conditions.add(QueryCondition(column, operator, value))
    }

}
