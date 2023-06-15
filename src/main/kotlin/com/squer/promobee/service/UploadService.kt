package com.squer.promobee.service

import com.squer.promobee.controller.dto.FileUploadDto
import com.squer.promobee.controller.dto.UploadLogDTO


interface UploadService {

    fun getGrnUploadLog(): List<UploadLogDTO>

    fun getTransporterUploadLog(): List<UploadLogDTO>

    fun getInvoiceUploadLog(): List<UploadLogDTO>

    fun transporterUpload(dto: FileUploadDto)



}