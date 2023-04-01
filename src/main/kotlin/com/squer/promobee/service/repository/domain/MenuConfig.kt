package com.squer.promobee.service.repository.domain

import com.squer.promobee.security.domain.NamedSquerEntity

class MenuConfig {
    var id: String?= null
    var name: String?= null
    var ciName: String?= null
    var url: String?= null
    var parentId: NamedSquerEntity?= null
    var level: Int?= null
    var displayOrder: Int?= null
    var prvId: NamedSquerEntity?= null
}