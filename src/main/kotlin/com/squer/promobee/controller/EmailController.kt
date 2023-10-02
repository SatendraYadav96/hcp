package com.squer.promobee.controller


import com.squer.promobee.service.EmailService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired


@Slf4j
open class EmailController@Autowired constructor(
    private val emailService: EmailService
) {

    private val log = LoggerFactory.getLogger(javaClass)






}