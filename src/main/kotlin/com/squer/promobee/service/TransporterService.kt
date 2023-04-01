package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.Transporter

interface TransporterService {

    fun getTransporter(): List<Transporter>
}