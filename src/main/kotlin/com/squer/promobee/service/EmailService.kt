package com.squer.promobee.service

import javax.servlet.http.HttpServletResponse

interface EmailService {

    fun getConsolidateExpiryReport(response: HttpServletResponse, index1:Int, index2:Int ):ByteArray

    fun SendTestMailForItemExpiry(response: HttpServletResponse, userId:String,index1:Int, index2:Int ):ByteArray

    fun SendTestMailForSampleExpiry(response: HttpServletResponse,userId:String, index1:Int, index2:Int ):ByteArray


    fun Send_Mail_optima(uploadId:String,response: HttpServletResponse):ByteArray

    fun SpecialDraftPlanReminder()

    fun SendMailFFSampleInputNearExpiry(uploadId:String ):ByteArray

    fun SendMailFFSampleInputExpired(uploadId:String ):ByteArray




}