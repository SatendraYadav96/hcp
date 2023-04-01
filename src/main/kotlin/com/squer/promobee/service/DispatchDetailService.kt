package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.DispatchDetail

interface DispatchDetailService {

    fun insertDispatchDetail(detail: DispatchDetail)
}