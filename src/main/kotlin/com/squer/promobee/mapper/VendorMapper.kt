package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.Vendor
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Select

@Mapper
interface VendorMapper : BaseMapper<VendorMapper>{
    /*
    @Select("select ID_VND from VENDOR_MASTER_VND where IS_ACTIVE_VND= 1 and CODE_VND = #{vendorCode}")
    @ResultMap("vendorResultMap")
    fun getVendorByCode(code: String): Vendor

    @Insert("insert into VENDOR_MASTER_VND (ID_VND, NAME_VND, CI_NAME_VND, CODE_VND, IS_ACTIVE_VND, CREATED_ON_VND, CREATED_BY_VND, UPDATED_ON_VND, UPDATED_BY_VND)" +
            " values(#{id}, #{name}, LOWER(#{ciName}), #{code}, #{active}, GETDATE(), #{createdBy}, GETDATE(), #{updatedBy})")
    fun insertVendor(vendor: Vendor)*/
}