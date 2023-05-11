package com.squer.promobee.service.repository


import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.UploadLog
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class
DashboardRepository(
    securityUtility: SecurityUtility
): BaseRepository<UploadLog>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    fun getPendingDispatch (): List<PendingDispacthDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getPendingDispatch", data)
    }

    fun getHubNearExpiry (): List<HubNearExpiryDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getHubNearExpiry", data)
    }


    fun getHubPendingRevalidation (): List<HUBPendingRevalidationDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getHubPendingRevalidation", data)
    }

    fun getHubGrnErrorLog (): List<HubGrnErrorLogDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getHubGrnErrorLog", data)
    }


    fun getItemExpiredDetails (): List<ItemExpiredDetailsDashboardDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("DashboardMapper.getItemExpiredDetails", data)
    }











}