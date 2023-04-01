package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.UploadLog

interface UploadLogService {
    fun getUploadLogByStatusId(statusId: String): List<UploadLog>
}