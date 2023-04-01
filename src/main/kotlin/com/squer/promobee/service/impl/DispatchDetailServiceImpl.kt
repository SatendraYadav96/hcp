package com.squer.promobee.service.impl

import com.squer.promobee.service.repository.DispatchDetailRepository
import com.squer.promobee.service.repository.domain.DispatchDetail
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class DispatchDetailServiceImpl @Autowired constructor(
        private val dispatchDetailRepository: DispatchDetailRepository
) {

    fun insertDispatchDetail(detail: DispatchDetail){
        dispatchDetailRepository.insertDispatchDetail(detail)
    }
}