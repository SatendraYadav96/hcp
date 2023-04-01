package com.squer.promobee.service.impl

import com.squer.promobee.service.UploadLogService
import com.squer.promobee.service.repository.UploadLogRepository
import com.squer.promobee.service.repository.domain.UploadLog
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class UploadLogServiceImpl @Autowired constructor(
        private val uploadLogRepository: UploadLogRepository
): UploadLogService {

    override fun getUploadLogByStatusId(statusId: String): List<UploadLog> {
        return uploadLogRepository.getUploadLogByStatusId(statusId)
    }
}