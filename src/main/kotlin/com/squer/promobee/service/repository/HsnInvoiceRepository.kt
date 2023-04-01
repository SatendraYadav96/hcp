package com.squer.promobee.service.repository

import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.HSN
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class HsnInvoiceRepository(
    securityUtility: SecurityUtility
): BaseRepository<HSN>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory



    fun addHsn(hsn: HSN){
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id", UUID.randomUUID().toString())
        hsn.hcmCode?.let { data.put("hcmCode", it) }
        hsn.rate?.let { data.put("rate", it) }
        data.put("createdBy", user.id )
        data.put("updatedBy", user.id)

        sqlSessionFactory.openSession().insert("HSNMapper.addHsn", data)
    }

    fun editInvoiceHeader(inh: InvoiceHeaderDTO){

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data : MutableMap<String , Any> =  mutableMapOf()

        inh.invoiceNo?.let { data.put("invoiceNo", it) }
        inh.weight?.let { data.put("weight", it) }
        inh.noOfBoxes?.let { data.put("noOfBoxes", it) }
        data.put("updatedBy", user.id)

        sqlSessionFactory.openSession().update("InvoiceHeaderMapper.editInvoiceHeader",data)
    }









}