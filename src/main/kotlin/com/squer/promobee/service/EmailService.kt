package com.squer.promobee.service

import javax.servlet.http.HttpServletResponse

interface EmailService {

    fun getConsolidateExpiryReport(response: HttpServletResponse, index1:Int, index2:Int ):ByteArray

    fun SendTestMailForItemExpiry(response: HttpServletResponse, index1:Int, index2:Int ):ByteArray


}