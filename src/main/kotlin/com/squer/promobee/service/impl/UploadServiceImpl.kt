package com.squer.promobee.service.impl


import com.squer.promobee.controller.dto.UploadLogDTO
import com.squer.promobee.service.UploadService
import com.squer.promobee.service.repository.UploadRepository
import com.squer.promobee.service.repository.domain.UploadLog
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*


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



}