package com.squer.promobee.controller


import com.squer.promobee.service.UploadService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired


@Slf4j
open class UploadController@Autowired constructor(
    private val uploadService: UploadService
) {

    private val log = LoggerFactory.getLogger(javaClass)






}