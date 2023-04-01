package com.squer.promobee.service.impl

import com.squer.promobee.service.TransporterService
import com.squer.promobee.service.repository.TransporterRepository
import com.squer.promobee.service.repository.domain.Transporter
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class TransporterServiceImpl @Autowired constructor(
        private val transporterRepository: TransporterRepository
) : TransporterService{

    override fun getTransporter(): List<Transporter>{
        return transporterRepository.getTransporter()
    }
}