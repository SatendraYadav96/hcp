package com.squer.promobee.service.repository

import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.GRNAcknowledgement
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository


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
        var data : MutableMap<String, Any> = mutableMapOf()
        data.put("id", id)
        return sqlSessionFactory.openSession().selectOne("GRNAcknowledgementMapper.getGrnById",data)
    }

    fun rejectAcknowledge(grnId: String, reason: String, userId: String){
        var data : MutableMap<String, Any> = mutableMapOf()
        data.put("grnId", grnId)
        data.put("reason", reason)
        data.put("userId", userId)
        sqlSessionFactory.openSession().update("GRNAcknowledgementMapper.rejectGrnAcknowledge",data)
    }

    fun approveAcknowledge(itcId: String, expiryDate: String, userId: String, grnId: String, medicalCode: String, hsnCode: String, ratePer: Int ){
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("itcId", itcId)
        data.put("expiryDate", expiryDate)
        data.put("userId", userId)
        data.put("grnId", grnId)
        data.put("medicalCode", medicalCode)
        data.put("hsnCode", hsnCode)
        data.put("ratePer", ratePer)
        sqlSessionFactory.openSession().update("GRNAcknowledgementMapper.approveAcknowledge",data)

    }

}