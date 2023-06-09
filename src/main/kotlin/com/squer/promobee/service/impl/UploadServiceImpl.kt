package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.UploadService
import com.squer.promobee.service.repository.UploadRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@Slf4j
class UploadServiceImpl @Autowired constructor(
    private val uploadRepository: UploadRepository


): UploadService {


    private val log = LoggerFactory.getLogger(javaClass)

    override fun getGrnUploadLog (): List<UploadLogDTO> {
        return uploadRepository.getGrnUploadLog()
    }

    override fun getTransporterUploadLog(): List<UploadLogDTO> {
        return uploadRepository.getTransporterUploadLog()
    }

    override fun getInvoiceUploadLog(): List<UploadLogDTO> {
        return uploadRepository.getInvoiceUploadLog()
    }

    override fun getAllUploadLog(typeId: String): List<UploadLogDTO> {
        return uploadRepository.getAllUploadLog(typeId)
    }

    override fun transporterUpload(dto: FileUploadDto) {
        return uploadRepository.transporterUpload(dto)
    }

    override fun transportExcelData(uplId : String): List<TransporterUploadDto> {
        return uploadRepository.transportExcelData(uplId)
    }

    override fun grnUpload(dto: FileUploadDto) {
        return uploadRepository.grnUpload(dto)
    }

    override fun grnExcelData(uplId: String): List<GrnUploadDTO> {
        return uploadRepository.grnExcelData(uplId)
    }

    override fun recipientUpload(dto: FileUploadDto) {
        return uploadRepository.recipientUpload(dto)
    }

    override fun recipientExcelData(uplId: String): List<RecipientUploadDTO> {
        return uploadRepository.recipientExcelData(uplId)
    }

    override fun virtualUpload(dto: FileUploadDto) {
        return uploadRepository.virtualUpload(dto)
    }

    override fun virtualSampleExcelData(uplId: String): List<VirtualSampleUploadDTO> {
        return uploadRepository.virtualSampleExcelData(uplId)
    }

    override fun getRecipientByCode(code: String): RecipientDTO {
        return uploadRepository.getRecipientByCode(code)
    }

    override fun getTransporterByName(name: String): TransporterDropdownDTO {
        return uploadRepository.getTransporterByName(name)
    }

    override fun getDoctorsByCode(code: String): RecipientDTO {
        return uploadRepository.getDoctorsByCode(code)
    }

    override fun invoiceUpload(dto: FileUploadDto) {
        return uploadRepository.invoiceUpload(dto)
    }

    override fun invoiceExcelData(uplId: String): List<InvoiceUploadDTO> {
        return uploadRepository.invoiceExcelData(uplId)
    }


}