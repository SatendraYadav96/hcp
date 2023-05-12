package com.squer.promobee.service.repository

import com.squer.promobee.service.repository.domain.ui.MenuAction
//import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
class MenuActionRepository{ //: JpaRepository<MenuAction, Long> {
    /*
    @Query(
        value = "SELECT m.* FROM MENU_ACTION m inner join SECURITY_ROLE_PRIVILEGES rp on rp.privilege_id = m.privilege_id " +
                " inner join SECURITY_ROLE r on r.id = rp.role_id inner join SECURITY_USER_ROLE ur on ur.role_id = r.id" +
                " where ur.user_id = :userId",
        nativeQuery = true) */
    fun findMenuActionByUser(userId: String): List<MenuAction>? {
        return null
    }

    fun findByParentId(parentId: Long?): List<MenuAction>? {
        return null
    }
}
