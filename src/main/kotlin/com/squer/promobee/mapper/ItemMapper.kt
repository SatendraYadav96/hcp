package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.Item
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.ResultType
import org.apache.ibatis.annotations.Select

@Mapper
interface ItemMapper: BaseMapper<Item> {




}