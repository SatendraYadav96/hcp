package com.squer.promobee.service.repository





import com.squer.promobee.controller.dto.OptimaDataLogsDTO
import com.squer.promobee.controller.dto.OverSamplingDetaislDTO
import com.squer.promobee.controller.dto.RecipientUnblockingPartialDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Users
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ComplianceRepository(
    securityUtility: SecurityUtility
): BaseRepository<Users>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun recipientUnblockingPartial(statusType: String, month: String, year: String): List<RecipientUnblockingPartialDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("statusType", statusType)
        data.put("month", month)
        data.put("year", year)

        var recipient : List<RecipientUnblockingPartialDTO> = ArrayList<RecipientUnblockingPartialDTO>()
        if(statusType == "1"){
            data.put("month", month)
            data.put("year", year)
            recipient =  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.recipientBlocked", data)
        }

        else if(statusType == "0"){
            data.put("month", month)
            data.put("year", year)
            recipient =  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.recipientUnblocked", data)
        }
        else {
            data.put("month", month)
            data.put("year", year)
            recipient =  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.recipientRejected", data)
        }

        return recipient



    }





    fun optimaMailLogs(type: String, month: String, year: String): List<OptimaDataLogsDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("type", type)
        data.put("month", month)
        data.put("year", year)

        var optima : List<OptimaDataLogsDTO> = ArrayList<OptimaDataLogsDTO>()

        if(type == "1"){
            data.put("month", month)
            data.put("year", year)

            optima =  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.sampleExpireInThirtyDays", data)
        }

        if(type == "2"){
            data.put("month", month)
            data.put("year", year)

            optima =  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.inputExpiring", data)

        }

        if(type == "Sample"){
            data.put("type", type)
            data.put("month", month)
            data.put("year", year)
            optima =  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.materialExpiring", data)
        }

        if(type == "Input"){
            data.put("type", type)
            data.put("month", month)
            data.put("year", year)
            optima =  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.materialExpiring", data)
        }


            return optima


    }





    fun overSamplingDetails(month: String, year: String): List<OverSamplingDetaislDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("month", month)
        data.put("year", year)

            return  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.overSamplingDetails", data)



    }











}