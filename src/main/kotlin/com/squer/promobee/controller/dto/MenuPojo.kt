package com.squer.promobee.controller.dto

import com.squer.promobee.api.v1.enums.UserMenuEnum
import com.squer.promobee.service.repository.domain.ui.MenuAction

class MenuPojo {
    constructor(menuAction: UserMenuEnum, childMenus: List<UserMenuEnum>?) {
        key = menuAction.key
        label = menuAction.label!!
        path = menuAction.path
        children = childMenus?.map{ MenuPojo(it, null)}
        title = menuAction.key
    }
    var key: String? = null
    lateinit var label: String
    var path: String? = null
    var title: String? = null
    var children: List<MenuPojo>?
}
