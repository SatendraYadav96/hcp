package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import net.bytebuddy.implementation.bytecode.Division
import org.apache.ibatis.annotations.Mapper

@Mapper
interface DivisionMapper : BaseMapper<Division>{
}