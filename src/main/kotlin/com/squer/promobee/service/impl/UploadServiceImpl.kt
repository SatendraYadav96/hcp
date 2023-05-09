package com.squer.promobee.service.impl


import com.squer.promobee.service.UploadService
import com.squer.promobee.service.repository.UploadRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class UploadServiceImpl @Autowired constructor(
    private val uploadRepository: UploadRepository


): UploadService {


    private val log = LoggerFactory.getLogger(javaClass)



}