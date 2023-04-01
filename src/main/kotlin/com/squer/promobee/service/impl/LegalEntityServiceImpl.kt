package com.squer.promobee.service.impl

import com.squer.promobee.service.repository.domain.LegalEntity
import com.squer.promobee.service.repository.LegalEntityRepository
import com.squer.promobee.service.LegalEntityService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class LegalEntityServiceImpl @Autowired constructor(
        private val legalEntityRepository: LegalEntityRepository
): LegalEntityService {

    override fun findById(id: String): LegalEntity {
        return legalEntityRepository.findById(id)
    }
}