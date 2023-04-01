package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.LegalEntity

interface LegalEntityService {

    fun findById(id:String): LegalEntity
}