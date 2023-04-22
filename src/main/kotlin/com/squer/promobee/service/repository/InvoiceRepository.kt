package com.squer.promobee.service.repository


import com.squer.promobee.api.v1.enums.TeamEnum
import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.controller.dto.InvoicePrintDetailsDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.DispatchDetailHo
import com.squer.promobee.service.repository.domain.Doctor
import com.squer.promobee.service.repository.domain.InvoiceHeader
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.io.File
import java.util.*


@Repository
class InvoiceRepository(
    securityUtility: SecurityUtility
): BaseRepository<InvoiceHeader>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    fun getInvoiceHeaderById(id:String):InvoiceHeader{
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String , Any> = mutableMapOf()

        data.put("id",id)

        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getInvoiceHeaderById",data)

    }

    fun printInvoice(inh:InvoiceHeaderDTO):List<InvoicePrintDetailsDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String,Any> = mutableMapOf()

        var inh = InvoiceHeaderDTO()
        var currDHO = DispatchDetailHo()
        var invoiceItemsPaging: Int? = 0

        var printDetails = InvoicePrintDetailsDTO(inh?.inhId!!)
        var invoice = inh.inhId?.let { getInvoiceHeaderById(it) }

        var doctor = Doctor()
        var doc = invoice?.recipientId.toString().also { doctor.id = it }

        var invoiceCutoffDate:String? = "invoiceCutoffDate"


        if(doc != null){
            inh.inhId?.let { data.put("id", it) }

             var invoicePrintDetails= return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.getVirtualPrintInvoiceHeaders",data)


        }else {

            var invoicePrintDetails = return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.getPrintInvoiceHeaders",data)
        }

        if(printDetails.employeeLRDate!! <= invoiceCutoffDate.toString()){
            val invoicePrint: File = ClassPathResource("src/main/resources/htmlPrint/print1.html").file
            var invoicePrintDetails = return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.getPrintInvoiceHeaders",data)
            var ho = TeamEnum.DEFAULT_HO_TEAM.also { printDetails.teamId = it.toString() }



        }











        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper",data)

    }













}