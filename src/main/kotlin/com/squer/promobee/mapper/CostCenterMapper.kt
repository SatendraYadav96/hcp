package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.CostCenter
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultType
import org.apache.ibatis.annotations.Select

@Mapper
interface CostCenterMapper: BaseMapper<CostCenter> {
/*
    @Select("select NAME_CCM from COST_CENTER_MASTER_CCM where ID_CCM = #{ccmId}")
    @ResultType(HashMap::class)
    fun getCostCenterNameById(ccmId: String?): Map<String, Any>

    @Select("select CODE_CCM from COST_CENTER_MASTER_CCM where ID_CCM= #{ccmId}")
    @ResultType(HashMap::class)
    fun getCostCenterCodeById(ccmId: String?): Map<String, Any>
    */
}