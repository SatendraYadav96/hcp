package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.SampleMaster

interface SampleMasterService {

    fun getSampleName(lmid: String): Map<String, Any>

    fun getSampleByLmid(lmid: String): SampleMaster

}