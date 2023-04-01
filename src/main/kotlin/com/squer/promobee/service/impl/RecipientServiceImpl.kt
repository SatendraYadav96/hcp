package com.squer.promobee.service.impl

import com.squer.promobee.service.RecipientService
import com.squer.promobee.service.repository.RecipientRepository
import com.squer.promobee.service.repository.domain.Recipient
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class RecipientServiceImpl @Autowired constructor(
        private val recipientRepository: RecipientRepository
): RecipientService {

    override fun getRecipients(teamId:String): List<MutableMap<String, Any>>{
        return recipientRepository.getRecipients(teamId)
    }

}