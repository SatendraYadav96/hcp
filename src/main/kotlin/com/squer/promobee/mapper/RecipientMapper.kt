package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.Recipient
import org.apache.ibatis.annotations.Mapper

@Mapper
interface RecipientMapper: BaseMapper<Recipient> {
}