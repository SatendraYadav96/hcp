package com.squer.promobee.service

import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.service.repository.domain.HSN

interface HsnInvoiceService {

  /*  fun addHsn(hcmCode: String, rate: String, userId: String)*/

    fun addHsn(hsn: HSN)

    fun editInvoiceHeader(inh: InvoiceHeaderDTO)

}