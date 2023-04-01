package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.UserDesignation
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Select

@Mapper
interface UserDesignationMapper: BaseMapper<UserDesignation> {
/*
    @Select("select * from USER_LOV_ULV where id_ulv=#{id}")
    @ResultMap("userDesignationResultMap")
    fun findById(id:String): UserDesignation*/
}