package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.HSN
import org.apache.ibatis.annotations.Mapper

@Mapper
interface HSNMapper : BaseMapper<HSN>{
}