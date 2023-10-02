package com.squer.promobee.service.impl


import com.squer.promobee.service.EmailService
import com.squer.promobee.service.repository.EmailRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class EmailServiceImpl @Autowired constructor(
    private val emailRepository: EmailRepository


): EmailService {



}