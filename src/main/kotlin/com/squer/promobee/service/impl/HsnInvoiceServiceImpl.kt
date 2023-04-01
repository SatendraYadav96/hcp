package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.HsnInvoiceService
import com.squer.promobee.service.repository.HsnInvoiceRepository
import com.squer.promobee.service.repository.domain.HSN
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class HsnInvoiceServiceImpl @Autowired constructor(
    private val hsnInvoiceRepository: HsnInvoiceRepository


): HsnInvoiceService {


    private val log = LoggerFactory.getLogger(javaClass)

/*    override fun addHsn(hcmCode: String, rate: String, userId: String) {

        hsnInvoiceRepository.addHsn(hcmCode, rate, userId)
    }*/

    override fun addHsn(hsn: HSN) {

            return hsnInvoiceRepository.addHsn(hsn)
        }


    override fun editInvoiceHeader(inh: InvoiceHeaderDTO) {
        return hsnInvoiceRepository.editInvoiceHeader(inh)
    }

}