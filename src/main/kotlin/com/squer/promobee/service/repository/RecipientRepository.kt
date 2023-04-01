package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.StatusEnum
import com.squer.promobee.persistence.BaseMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Recipient
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class RecipientRepository @Autowired constructor(
        securityUtility: SecurityUtility
):BaseRepository<Recipient>(
        securityUtility  = securityUtility
){

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getRecipients(teamId:String): List<MutableMap<String, Any>>{
        var data : MutableMap<String, Any> = mutableMapOf()
        data.put("teamId", teamId)
        data.put("statusId", StatusEnum.RECIPIENT_STATUS_ACTIVE_ID.id)
        return sqlSessionFactory.openSession().selectList("RecipientMapper.recipientSelect", data)
    }
}