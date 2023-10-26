package com.squer.promobee.service.impl


import com.squer.promobee.service.EmailService
import com.squer.promobee.service.repository.EmailRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse
import kotlin.system.measureTimeMillis


@Service
@Slf4j
class EmailServiceImpl @Autowired constructor(
    private val emailRepository: EmailRepository



    ): EmailService {

    override fun getConsolidateExpiryReport(response: HttpServletResponse, index1:Int, index2:Int): ByteArray
    {
        return emailRepository.getConsolidateExpiryReport(response, index1, index2)
    }

    override fun SendTestMailForItemExpiry(response: HttpServletResponse, index1: Int, index2: Int): ByteArray {
        return emailRepository.SendTestMailForItemExpiry(response,index1,index2)
    }


    override fun SendTestMailForSampleExpiry(response: HttpServletResponse, index1: Int, index2: Int): ByteArray {
        return emailRepository.SendTestMailForSampleExpiry(response,index1,index2)
    }


    override fun Send_Mail_optima(response: HttpServletResponse, uploadId: String): ByteArray {
        return emailRepository.Send_Mail_optima(response,uploadId)
    }

    override fun SpecialDraftPlanReminder() {
        return emailRepository.SpecialDraftPlanReminder()
    }

    override fun SendMailFFSampleInputNearExpiry( uploadId: String):ByteArray {
        return emailRepository.SendMailFFSampleInputNearExpiry(uploadId)
    }

    override fun SendMailFFSampleInputExpired(uploadId: String): ByteArray {
        return emailRepository.SendMailFFSampleInputExpired(uploadId)
    }




}