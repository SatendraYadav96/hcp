package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.UserStatus
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Select

@Mapper
interface UserStatusMapper: BaseMapper<UserStatus> {
    /*
    @Select("SELECT * FROM SYSTEM_LOV_SLV")
    @ResultMap("userStatusResultMap")
    fun getStatusList(): List<UserStatus>*/
}