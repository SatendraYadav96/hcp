package com.squer.promobee.service

import com.squer.promobee.controller.dto.UploadLogDTO
import com.squer.promobee.service.repository.domain.UploadLog


interface UploadService {

    fun getGrnUploadLog(): List<UploadLogDTO>

    fun getTransporterUploadLog(): List<UploadLogDTO>

    fun getInvoiceUploadLog(): List<UploadLogDTO>



}