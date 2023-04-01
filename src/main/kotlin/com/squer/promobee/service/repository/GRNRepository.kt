package com.squer.promobee.service.repository

import com.squer.promobee.mapper.GRNAcknowledgementMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.GRNAcknowledgement
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class GRNRepository(
    securityUtility: SecurityUtility
): BaseRepository<GRNAcknowledgement> (
     securityUtility = securityUtility,
){

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getUnacknowledgeData(): List<GRNAcknowledgement>{
        return sqlSessionFactory.openSession().selectList("GRNAcknowledgementMapper.grn_acknowledgement_select")
    }

    fun getAcknowledgeDataById(id: String): GRNAcknowledgement{
        return sqlSessionFactory.openSession().selectOne("GRNAcknowledgementMapper.getGrnById")
    }

    fun rejectAcknowledge(grnId: String, reason: String, userId: String){
        var data : MutableMap<String, Any> = mutableMapOf()
        data.put("grnId", grnId)
        data.put("reason", reason)
        data.put("userId", userId)
        sqlSessionFactory.openSession().update("GRNAcknowledgementMapper.rejectGrnAcknowledge")
    }

    fun approveAcknowledge(itcId: String, expiryDate: Date, userId: String, grnId: String, medicalCode: String? = null, hsnCode: String?= null, ratePer: Int?= null){
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("itcId", itcId)
        data.put("expiryDate", expiryDate)
        data.put("userId", userId)
        data.put("grnId", grnId)
        data.put("medicalCode", medicalCode!!)
        data.put("hsnCode", hsnCode!!)
        data.put("ratePer", ratePer!!)
        sqlSessionFactory.openSession().update("GRNAcknowledgementMapper.approveAcknowledge")
    }
}