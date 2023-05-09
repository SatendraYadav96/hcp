package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.DispatchDetail
import org.springframework.web.bind.annotation.PathVariable

interface DispatchDetailService {

    fun insertDispatchDetail(detail: DispatchDetail)

    fun getPickList( teamId: String,  month: Int,  year: Int,  isSpecial: Int)

}