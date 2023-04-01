package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.UserDesignation

interface UserDesignationService {

    fun findById(id:String): UserDesignation
}