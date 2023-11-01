package com.squer.promobee.service.repository





import com.squer.promobee.api.v1.enums.UserLovEnum
import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.ComplianceDetails
import com.squer.promobee.service.repository.domain.UserDesignation
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





    fun overSamplingDetails(month: String, year: String): List<ComplianceListCrudDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        val format = SimpleDateFormat("MM/yyyy", Locale.US)
        val date = format.parse("$month/$year")
        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstDayOfMonth = calendar.time

        calendar.add(Calendar.MONTH, 2)





        var firstDayOfThirdMonth = calendar.time

//        data.put("month", firstDayOfMonth)
//        data.put("year", firstDayOfThirdMonth)

        var listComp = mutableListOf<ComplianceListCrudDTO>()

        var comp = mutableListOf<ComplianceDetails>()

        if(user.userDesignation!!.id == UserLovEnum.BEX.id || user.userDesignation!!.id == UserLovEnum.COMPLIANCE_ADMIN.id ){

            var data0: MutableMap<String, Any> = mutableMapOf()

            data0.put("searchFromDate",firstDayOfMonth)
            data0.put("searchToDate",firstDayOfThirdMonth)

            comp = sqlSessionFactory.openSession().selectList<ComplianceDetails>("ComplianceDetailsMapper.overSamplingDetailsComplianceData",data0)

            comp.forEach {

                var crud = ComplianceListCrudDTO()

                crud.ID = it.id
                crud.Designation = user.userDesignation!!.id
                crud.FFCode = it.ff
                var ffCode = it.ff
                crud.FFName = sqlSessionFactory.openSession().selectOne("RecipientMapper.overSamplingDetailsRecipient",ffCode)
                crud.RBM = it.rbm
                crud.AM = it.am
                crud.BU = it.bu
                crud.Team_Name = it.teamName
                crud.DR_ID = it.drId
                crud.StartDate = it.startDate
                crud.EndDate = it.endDate
                crud.Remarks = it.remarks
                if(it.reason.isNullOrEmpty()){
                    crud.Reason = ""
                }else{
                    var id = it.reason
                    var data1: MutableMap<String, Any> = mutableMapOf()
                    data1.put("id",id!!)
                    data1.put("type","COMPLIANCE_REASONS")

                    crud.Reason = sqlSessionFactory.openSession().selectOne("UserDesignationMapper.overSamplingDetailsReason",data1)


                }
                crud.Totalsamplegiven = it.totSampleGiven
                crud.DRName = it.drName

                listComp.addAll(listOf(crud))
            }



        } else{
            var usr = user.id
            var nameBu = sqlSessionFactory.openSession().selectList<ComplianceModeldeDTO>("ComplianceDetailsMapper.buNameData",usr)

            nameBu.forEach {
                if(it.BU_NAME == "DIABETES" || it.BU_NAME == "CNS" || it.BU_NAME == "GEMS"){

                    var data: MutableMap<String, Any> = mutableMapOf()
                    data.put("usr",usr)
                    data.put("FromDate",firstDayOfMonth)
                    data.put("ToDate",firstDayOfThirdMonth)

                    comp = sqlSessionFactory.openSession().selectList<ComplianceDetails>("ComplianceDetailsMapper.SearchCompliance",data)

                    comp.forEach {

                        if(it.reason.isNullOrEmpty()){

                        var crud = ComplianceListCrudDTO()

                        crud.ID = it.id
                        crud.Designation = user.userDesignation!!.id
                        crud.FFCode = it.ff
                        var ffCode = it.ff
                        crud.FFName = sqlSessionFactory.openSession()
                            .selectOne("RecipientMapper.overSamplingDetailsRecipient", ffCode)
                        crud.RBM = it.rbm
                        crud.AM = it.am
                        crud.BU = it.bu
                        crud.Team_Name = it.teamName
                        crud.DR_ID = it.drId
                        crud.StartDate = it.startDate
                        crud.EndDate = it.endDate
                        crud.Remarks = it.remarks
                        crud.Totalsamplegiven = it.totSampleGiven
                        crud.DRName = it.drName
                        if (it.reason.isNullOrEmpty()) {
                            crud.Reason = ""
                        } else {
                            var id = it.reason
                            var data1: MutableMap<String, Any> = mutableMapOf()
                            data1.put("id", id!!)
                            data1.put("type", "COMPLIANCE_REASONS")

                            crud.Reason = sqlSessionFactory.openSession()
                                .selectOne("UserDesignationMapper.overSamplingDetailsReason", data1)


                        }
                        var data1: MutableMap<String, Any> = mutableMapOf()
                        data1.put("type", "COMPLIANCE_REASONS")
                        var teams = sqlSessionFactory.openSession()
                            .selectList<UserDesignation>("UserDesignationMapper.overSamplingDetailsReasonList", data1)

                        crud.ReasonId = it.reason
                        listComp.addAll(listOf(crud))

                    }





                    }
                }else{



                    var data0: MutableMap<String, Any> = mutableMapOf()

                    data0.put("searchFromDate",firstDayOfMonth)
                    data0.put("searchToDate",firstDayOfThirdMonth)

                    comp = sqlSessionFactory.openSession().selectList<ComplianceDetails>("ComplianceDetailsMapper.overSamplingDetailsComplianceData",data0)


                    var i = 0

                    comp.forEach {
//                        var usr = user.id
//                        var nameBu = sqlSessionFactory.openSession().selectList<ComplianceModeldeDTO>("ComplianceDetailsMapper.buNameData",usr)

                        if(nameBu[i].BU_NAME == it.bu){

                            if(it.reason.isNullOrEmpty()){
                                var crud = ComplianceListCrudDTO()

                                crud.ID = it.id
                                crud.Designation = user.userDesignation!!.id
                                crud.FFCode = it.ff
                                var ffCode = it.ff
                                crud.FFName = sqlSessionFactory.openSession()
                                    .selectOne("RecipientMapper.overSamplingDetailsRecipient", ffCode)
                                crud.RBM = it.rbm
                                crud.AM = it.am
                                crud.BU = it.bu
                                crud.Team_Name = it.teamName
                                crud.DR_ID = it.drId
                                crud.StartDate = it.startDate
                                crud.EndDate = it.endDate
                                crud.Remarks = it.remarks
                                crud.Totalsamplegiven = it.totSampleGiven
                                crud.DRName = it.drName
                                if (it.reason.isNullOrEmpty()) {
                                    crud.Reason = ""
                                } else {
                                    var id = it.reason
                                    var data1: MutableMap<String, Any> = mutableMapOf()
                                    data1.put("id", id!!)
                                    data1.put("type", "COMPLIANCE_REASONS")

                                    crud.Reason = sqlSessionFactory.openSession()
                                        .selectOne("UserDesignationMapper.overSamplingDetailsReason", data1)


                                }
                                var data1: MutableMap<String, Any> = mutableMapOf()
                                data1.put("type", "COMPLIANCE_REASONS")
                                var teams = sqlSessionFactory.openSession()
                                    .selectList<UserDesignation>("UserDesignationMapper.overSamplingDetailsReasonList", data1)

                                crud.ReasonId = it.reason
                                listComp.addAll(listOf(crud))
                            }

                        }

                    }

                }
            }


        }








            return  listComp



    }


    fun masterBlockedList(year: String): List<RecipientBlockedListCrudDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var calendar: Calendar = Calendar.getInstance()





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
            //calendar.time = it.REC_UPDATED_ON_LOG
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



    fun saveMasterBlockedRecipient(blockRecp: List<SaveRecipientBlockedmasterDTO>){
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var isSuccess = false

        var calendar: Calendar = Calendar.getInstance()

        // Create a SimpleDateFormat object to format the date
        var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        // Format the current date to the desired format
        var currentDate: String = formatter.format(calendar.time)



        blockRecp.forEach {
            if(it.remark != null || it.id != "" ){

                var cdId = it.id

                var cd = sqlSessionFactory.openSession().selectOne<RecipientBlockLogsDTO>("RecipientMapper.SaveMasterBlockedRecipientLog",cdId)

                if(it.isBlocked == false && it.remark.equals(cd.REC_REMARKS_LOG) != true){

                    var data: MutableMap<String, Any> = mutableMapOf()
                    data.put("code",cd.REC_CODE_LOG!!)
                    data.put("blockedType","UPLOADED")

                    var blockedDate = sqlSessionFactory.openSession().selectOne<RecipientBlockLogsDTO>("RecipientMapper.SaveMasterBlockedRecipientBlockedDate",data)

                    var ffBlockedLog = RecipientBlockLogsDTO()

                    ffBlockedLog.ID_REC_LOG = UUID.randomUUID().toString()
                    ffBlockedLog.REC_CODE_LOG = cd.REC_CODE_LOG
                    ffBlockedLog.REC_ISBLOCKED_LOG = it.isBlocked.toString()
                    ffBlockedLog.REC_BLOCKED_BY_LOG = user.id
                    ffBlockedLog.REC_BLOCKED_ON_LOG =  cd.REC_BLOCKED_ON_LOG
                    ffBlockedLog.REC_UPDATED_BY_LOG = user.id
                    ffBlockedLog.REC_UPDATED_ON_LOG = cd.REC_BLOCKED_ON_LOG
                    ffBlockedLog.REC_REMARKS_LOG = it.remark
                    ffBlockedLog.REC_BLOCKEDTYPE_LOG = "MASTER_ADMIN_UNBLOCK"


                    sqlSessionFactory.openSession().insert("RecipientMapper.insertMasterBlockedRecipient",ffBlockedLog)

                    isSuccess = true



                } else{
                    isSuccess = true
                }

            }
        }


    }




    fun saveOverSampling(comp: List<SaveOverSamplingDTO>) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        comp.forEach {
            if(it.id != ""){
                if(user.userDesignation!!.id == UserLovEnum.BEX.id || user.userDesignation!!.id == UserLovEnum.COMPLIANCE_ADMIN.id ){

                    if(it.remark != null && it.remark != ""){

                        var cdId = it.id

                        var cd = sqlSessionFactory.openSession().selectOne<ComplianceDetails>("ComplianceDetailsMapper.saveOverSampling",cdId)

                        var data: MutableMap<String, Any> = mutableMapOf()

                        data.put("remark",it.remark!!)
                        data.put("id",cdId!!)
                        sqlSessionFactory.openSession().update("ComplianceDetailsMapper.saveOverSamplingRemark",data)

                    }

                }else{
                    if (it.reason != null && it.remark != ""){
                        var cdId = it.id

                        var cd = sqlSessionFactory.openSession().selectOne<ComplianceDetails>("ComplianceDetailsMapper.saveOverSampling",cdId)

                        var data: MutableMap<String, Any> = mutableMapOf()

                        data.put("reason",it.reason!!)
                        data.put("id",cdId!!)
                        sqlSessionFactory.openSession().update("ComplianceDetailsMapper.saveOverSamplingReason",data)
                    }
                }
            }

        }
    }














}