package com.squer.promobee.service.repository





import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Users
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.text.SimpleDateFormat
import java.util.*

@Repository
class ComplianceRepository(
    securityUtility: SecurityUtility,
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
            recipient =  sqlSessionFactory.openSession().selectList ("ComplianceDetailsMapper.recipientBlocked", data)
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



        // Calculate the first day of the month
        val format = SimpleDateFormat("MM/yyyy", Locale.US)
        val date = format.parse("$month/$year")
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstDayOfMonth = calendar.time

        calendar.add(Calendar.MONTH, 2)

        var firstDayOfThirdMonth = calendar.time

        // Calculate the first day of the third month
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
//        calendar.set(Calendar.MONTH, )
//        calendar.set(Calendar.DAY_OF_MONTH, 1)
//        val firstDayOfThirdMonth = calendar.time


        data.put("month", firstDayOfMonth)
        data.put("year", firstDayOfThirdMonth)




            return  sqlSessionFactory.openSession().selectList("ComplianceDetailsMapper.overSamplingDetails", data)



    }


    fun masterBlockedList(year: String): List<RecipientBlockedListCrudDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()



        data.put("year",year)

        var recipientBlockedlist =   sqlSessionFactory.openSession().selectList<RecipientBlockLogsDTO>("ComplianceDetailsMapper.masterBlockedList",data)

        var blockedListRecipient = mutableListOf<RecipientBlockedListCrudDTO>()

        var i = 1

        recipientBlockedlist.forEach {
            var teamId = ""
            var blockedList = RecipientBlockedListCrudDTO()
            var ffCode = it.REC_CODE_LOG
            blockedList.ID = it.ID_REC_LOG
            blockedList.EmployeeCode = it.REC_CODE_LOG
            blockedList.EmployeeName = sqlSessionFactory.openSession().selectOne("RecipientMapper.masterBlockedListFFName",ffCode)
             teamId = sqlSessionFactory.openSession().selectOne("RecipientMapper.masterBlockedListTeamId",ffCode)
            blockedList.Team = sqlSessionFactory.openSession().selectOne("RecipientMapper.masterBlockedListTeam",teamId)
            blockedList.Headquarter = sqlSessionFactory.openSession().selectOne("RecipientMapper.masterBlockedListHq",ffCode)
            blockedList.AM = sqlSessionFactory.openSession().selectOne("RecipientMapper.masterBlockedListAm",ffCode)
            blockedList.RBM = sqlSessionFactory.openSession().selectOne("RecipientMapper.masterBlockedListRbm",ffCode)
            var dateString = it.REC_UPDATED_ON_LOG
            var calendar = Calendar.getInstance()
            calendar.time = it.REC_UPDATED_ON_LOG
            var monthValue = calendar.get(Calendar.MONTH) + 1
            var yearValue = calendar.get(Calendar.YEAR)
            blockedList.Month = monthValue.toString()
            blockedList.Year = yearValue.toString()
            blockedList.Blocked_On = it.REC_BLOCKED_ON_LOG
            blockedList.IsBockedFF = it.REC_ISBLOCKED_LOG
            blockedList.Remark = it.REC_REMARKS_LOG
            blockedList.Blocked_type = it.REC_BLOCKEDTYPE_LOG

            blockedListRecipient.addAll(listOf(blockedList))

            i++
        }

      return blockedListRecipient

    }








}