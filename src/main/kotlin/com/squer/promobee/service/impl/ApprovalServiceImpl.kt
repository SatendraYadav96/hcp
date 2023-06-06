package com.squer.promobee.service.impl


import com.squer.promobee.service.ApprovalService
import com.squer.promobee.service.repository.ApprovalRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class ApprovalServiceImpl @Autowired constructor(
    private val approvalRepository: ApprovalRepository


): ApprovalService {


    private val log = LoggerFactory.getLogger(javaClass)


}