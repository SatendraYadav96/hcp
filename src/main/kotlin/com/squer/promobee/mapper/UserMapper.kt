package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.security.domain.User
import org.apache.ibatis.annotations.*

@Mapper
interface UserMapper: BaseMapper<User> {
    /*
    @Select("select u.ID_USR, u.NAME_USR, u.CI_NAME_USR, u.EMAIL_ADDRESS_USR, u.EMPLOYEE_CODE_USR, u.ACTIVE_FROM_USR, " +
            "u.ACTIVE_TO_USR, u.LAST_LOGGED_IN_USR, u.LOGIN_NAME_USR, u.CREATED_BY_USR, u.CREATED_ON_USR, u.UPDATED_BY_USR, " +
            "u.UPDATED_ON_USR, u.ID_ETY_USR, NAME_ETY, u.ID_DESG_ULV_USR, NAME_ULV, u.ID_STATUS_SLV_USR, NAME_SLV " +
            "from USER_MASTER_USR u" +
            " left join USER_LOV_ULV d on d.ID_ULV = u.ID_DESG_ULV_USR " +
            " left join SYSTEM_LOV_SLV s on s.ID_SLV = u.ID_STATUS_SLV_USR " +
            " left join LEGAL_ENTITY_MASTER_ETY e on e.ID_ETY = u.ID_ETY_USR " +
            " where u.id_usr=#{id}")
    @ResultMap("usersResultMap")
    fun findUserById(id: String): User?

    @Select("SELECT * from USER_MASTER_USR where login_name_usr = #{username}")
    @ResultMap("usersResultMap")
    fun findByUsername(username: String): User?

    //TODO
    @Insert("<script>INSERT INTO USER_MASTER_USR(name_usr, ci_name_usr, employee_code_usr, id_desg_ulv_usr, active_from_usr, active_to_usr, id_ety_usr, " +
            "email_address_usr, login_name_usr, last_logged_in_usr, id_status_slv_usr, created_on_usr, created_by_usr, updated_on_usr, updated_by_usr) " +
            " values (#{fullName}, #{ciName}, #{employeeCode}, #{userDesignation.id}, #{activeFrom}, #{activeTo}, #{legalEntity.id}, " +
            "#{email}, #{username}, #{lastLoggedIn}, #{status.id}, #{createdAt}, #{createdBy}, #{updatedAt}, #{updatedBy})</script>")
    override fun insert(user: User)

    //TODO
    @Update("UPDATE USERS set name_usr=#{fullName}, ci_name_usr=#{ciName}, login_name_usr=#{username}, employee_code_usr=#{employeeCode}," +
            "id_desg_ulv_usr=#{userDesignation.id}, active_from_usr=#{activeFrom}, active_to_usr=#{activeTo}, id_ety_usr=#{legalEntity.id}, " +
            "email_address_usr=#{email}, last_loggd_in_usr=#{lastLoggedIn}, id_status_slv_usr=#{status.id}, updated_on_usr=#{updatedAt}, updated_by_usr=#{updatedBy} where id_usr=#{id}")
    override fun update(user: User)
*/
}
