package com.squer.promobee.service

import com.squer.promobee.controller.dto.*


interface UploadService {

    fun getGrnUploadLog(): List<UploadLogDTO>

    fun getTransporterUploadLog(): List<UploadLogDTO>

    fun getInvoiceUploadLog(): List<UploadLogDTO>

    fun getAllUploadLog(typeId : String): List<UploadLogDTO>

    fun transporterUpload(dto: FileUploadDto)

    fun transportExcelData(uplId : String): List<TransporterUploadDto>

    fun grnUpload(dto: FileUploadDto)

    fun grnExcelData(uplId : String): List<GrnUploadDTO>

    fun recipientUpload(dto: FileUploadDto)

    fun recipientExcelData(uplId : String): List<RecipientUploadDTO>

    fun virtualUpload(dto: FileUploadDto)

    fun virtualSampleExcelData(uplId : String): List<VirtualSampleUploadDTO>

    fun getRecipientByCode(code : String): RecipientDTO

    fun getTransporterByName(name : String): TransporterDropdownDTO

    fun invoiceUpload(dto: FileUploadDto)

    fun getDoctorsByCode(code : String): RecipientDTO

    fun invoiceExcelData(uplId : String): List<InvoiceUploadDTO>

    fun getVirtualSampleUploadLog(): List<UploadLogDTO>

    fun getRecipientUploadLog(): List<UploadLogDTO>



}