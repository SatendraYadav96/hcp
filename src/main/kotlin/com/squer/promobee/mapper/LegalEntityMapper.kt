package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.LegalEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Select

//@Mapper
interface LegalEntityMapper : BaseMapper<LegalEntity>{
/*
    @Select("select * from LEGAL_ENTITY_MASTER_ETY where id_ety=#{id}")
    @ResultMap("legalEntityResultMap")
    fun findById(id: String): LegalEntity*/
}