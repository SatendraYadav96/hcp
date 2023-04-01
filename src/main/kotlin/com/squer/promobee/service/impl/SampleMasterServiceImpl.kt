package com.squer.promobee.service.impl

import com.squer.promobee.service.SampleMasterService
import com.squer.promobee.service.repository.SampleMasterRepository
import com.squer.promobee.service.repository.domain.SampleMaster
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class SampleMasterServiceImpl @Autowired constructor(
        private val sampleMasterRepository: SampleMasterRepository
): SampleMasterService{

    override fun getSampleName(lmid: String): Map<String, Any>{
        return sampleMasterRepository.getSampleName(lmid)
    }

    override fun getSampleByLmid(lmid: String): SampleMaster
    {
        return sampleMasterRepository.getSampleByLmid(lmid)
    }
}