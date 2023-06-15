package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.FileUploadDto
import com.squer.promobee.controller.dto.UploadLogDTO
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

    override fun transporterUpload(dto: FileUploadDto) {
        return uploadRepository.transporterUpload(dto)
    }



}