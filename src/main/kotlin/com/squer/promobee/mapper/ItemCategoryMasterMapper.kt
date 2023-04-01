package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.ItemCategoryMaster
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.ResultType
import org.apache.ibatis.annotations.Select

@Mapper
interface ItemCategoryMasterMapper : BaseMapper<ItemCategoryMaster>{
/*
    @Select("select * from ITEM_CATEGORY_MASTER_ITC")
    @ResultMap("itemCategoryResultMap")
    fun getItemCategory(): List<ItemCategoryMaster>

    @Select("select * from ITEM_CATEGORY_MASTER_ITC where ID_ITC = #{itcId}")
    @ResultMap("itemCategoryResultMap")
    fun getItemCategoryById(id: String): ItemCategoryMaster
*/
}