package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.SampleMaster
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.ResultType
import org.apache.ibatis.annotations.Select

//@Mapper
interface SampleMasterMapper: BaseMapper<SampleMaster> {
    /*
    @Select("select NAME_BRD from SAMPLE_MASTER_SMP join BRAND_MASTER_BRD " +
            "on ID_BRD_SMP = ID_SMP where LMID_SMP is not null and lower(LMID_SMP) =#{lmid}")
    @ResultType(HashMap:: class)
    fun getSampleName(lmid: String): Map<String, Any>

    @Select("select * from SAMPLE_MASTER_SMP where LMID_SMP = #{lmId}")
    @ResultMap("sampleMasterResultMap")
    fun getSampleByLmid(lmid: String): SampleMaster*/
}