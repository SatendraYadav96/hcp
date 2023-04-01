package com.squer.promobee.service


import com.squer.promobee.service.repository.domain.ui.MenuAction


interface MenuActionService {
    fun findMenuActionByUser(userId: String): List<MenuAction>?

    fun findParentMenus(parentId: Long?): List<MenuAction>?
}
