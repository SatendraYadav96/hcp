package com.squer.promobee.service.impl

import com.squer.promobee.service.CostCenterService
import com.squer.promobee.service.repository.CostCenterRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class CostCenterServiceImpl @Autowired constructor(
        private val costCenterRepository: CostCenterRepository
): CostCenterService{

    override fun getCostCenterNameById(id: String?): Map<String, Any>{
        return costCenterRepository.getCostCenterNameById(id)
    }

    override fun getCostCenterCodeById(ccmId: String?): Map<String, Any>{
        return costCenterRepository.getCostCenterCodeById(ccmId)
    }
}