package com.squer.promobee.mapper

import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.service.repository.domain.GRNAcknowledgement
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import java.util.Date

//@Mapper
interface GRNAcknowledgementMapper: BaseMapper<GRNAcknowledgement>{
    /*
    @Select("select * from dbo.GRN_ACKNOWLEDGEMENT_GRN where IS_ACKNOWLEDGED_GRN = 0")
    @ResultMap("grnAcknowledgementResultMap")
    fun getUnacknowledgeData(): List<GRNAcknowledgement>

    @Select("Select * from GRN_ACKNOWLEDGEMENT_GRN where ID_GRN=#{id}")
    @ResultMap("grnAcknowledgementResultMap")
    fun getAcknowledgeDataById(id: String): GRNAcknowledgement

    @Update("update GRN_ACKNOWLEDGEMENT_GRN set IS_ACKNOWLEDGED_GRN= 2, UPDATED_ON_GRN=GETDATE(), UPDATED_BY_GRN=#{userId}, ACKNOWLEDGE_REMARKS_GRN = #{reason} where ID_GRN = #{grnId}")
    fun rejectAcknowledge(grnId: String, reason: String, userId: String)

    @Update("<script>update GRN_ACKNOWLEDGEMENT_GRN set IS_ACKNOWLEDGED_GRN = 1, ACKNOWLEDGED_ON_DATE_GRN = GETDATE(), ID_ITC_GRN = #{itcId}, EXPIRY_DATE_GRN = #{expiryDate}," +
            " UPDATED_ON_GRN = GETDATE(), UPDATED_BY_GRN = #{userId}" +
            " <if test=\"medicalCode != null\">, LINE_TEXT_GRN = #{medicalCode}</if> " +
            " <if test=\"hsnCode != null\">, HSN_CODE_GRN = #{hsnCode}</if> " +
            " <if test=\"ratePer != null\">, RATE_PER_GRN = #{ratePer}</if>" +
            " where ID_GRN = #{grnId}</script>")
    fun approveAcknowledge(itcId: String, expiryDate: Date, userId: String, grnId: String, medicalCode: String? = null, hsnCode: String?= null, ratePer: Int?= null)*/
}