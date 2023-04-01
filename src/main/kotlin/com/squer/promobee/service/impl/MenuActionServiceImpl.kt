package com.squer.promobee.service.impl

import com.squer.promobee.service.repository.domain.ui.MenuAction
import com.squer.promobee.service.repository.MenuActionRepository
import com.squer.promobee.service.MenuActionService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class MenuActionServiceImpl @Autowired constructor(
    private val menuActionRepository: MenuActionRepository
) : MenuActionService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun findMenuActionByUser(userId: String): List<MenuAction>? {
        return menuActionRepository.findMenuActionByUser(userId)
    }

    override fun findParentMenus(parentId: Long?): List<MenuAction>? {
        return menuActionRepository.findByParentId(parentId)
    }
}
