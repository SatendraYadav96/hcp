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

    override fun getVirtualSampleUploadLog(): List<UploadLogDTO> {
        return uploadRepository.getVirtualSampleUploadLog()
    }

    override fun getRecipientUploadLog(): List<UploadLogDTO> {
        return uploadRepository.getRecipientUploadLog()
    }

    override fun getNonComplianceUploadLog(): List<UploadLogDTO> {
        return uploadRepository.getNonComplianceUploadLog()
    }

    override fun getOverSamplingUploadLog(): List<UploadLogDTO> {
        return uploadRepository.getOverSamplingUploadLog()
    }

    override fun getOverSamplingDetailsUploadLog(): List<UploadLogDTO> {
        return uploadRepository.getOverSamplingDetailsUploadLog()
    }

    override fun getMaterialExpiryUploadLog(): List<UploadLogDTO> {
        return uploadRepository.getMaterialExpiryUploadLog()
    }

    override fun nonComplianceUpload(dto: FileUploadDto) {
        return uploadRepository.nonComplianceUpload(dto)
    }

    override fun overSamplingUpload(dto: FileUploadDto) {
        return uploadRepository.overSamplingUpload(dto)
    }

    override fun overSamplingDetailsUpload(dto: FileUploadDto) {
        return uploadRepository.overSamplingDetailsUpload(dto)
    }


    override fun materialExpiryUpload(dto: FileUploadDto) {
        return uploadRepository.materialExpiryUpload(dto)
    }

    override fun nonComplianceExcelData(uplId: String): List<OptimaMiUploadDTO> {
        return uploadRepository.nonComplianceExcelData(uplId)
    }

    override fun overSamplingExcelData(uplId: String): List<overSamplingUploadDTO> {
        return uploadRepository.overSamplingExcelData(uplId)
    }

    override fun overSamplingDetailsExcelData(uplId: String): List<OverSamplingDetailsUploadDTO> {
        return uploadRepository.overSamplingDetailsExcelData(uplId)
    }

    override fun materialExpiryExcelData(uplId: String): List<OptimaMiUploadDTO> {
        return uploadRepository.materialExpiryExcelData(uplId)
    }

    override fun multipleAllocationUpload(dto: MultipleAllocationUploadDTO) {
        return uploadRepository.multipleAllocationUpload(dto)
    }


}