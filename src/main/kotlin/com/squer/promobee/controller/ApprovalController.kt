package com.squer.promobee.controller


import com.squer.promobee.service.ApprovalService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired


@Slf4j
open class ApprovalController@Autowired constructor(
    private val approvalService: ApprovalService
) {

    private val log = LoggerFactory.getLogger(javaClass)




}