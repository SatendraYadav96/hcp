package com.squer.cme.utils

import org.reflections.Reflections.log
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class EmailUtils {

    @Value("\${application.email.URI}")
    var URI: String = "http://robustnext.squer.co.in:7272/v1/notification/send/b0b4b4d9-2d11-4772-aba0-32dd880c57ff?type=EMAIL"

    @Value("\${application.test.email}")
    var testEmail: String = "PROD"

    @Throws(Exception::class)
    fun sendMail(mailTo: MutableList<String?>, cc: MutableList<String>?, body: String?, subject: String?): Boolean {
        try {
            val restTemplate = RestTemplate()
            val uri =
                URI(URI)
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            var bodyToSend = body
            if (testEmail != "PROD") bodyToSend += "<br/> ${mailTo.joinToString (",")} cc: ${cc?.firstOrNull()?:"-"}"
            val request: HttpEntity<List<Notification>> = HttpEntity(
                listOf(
                    Notification(
                        subject,
                        bodyToSend,
                        userToken = if (testEmail == "PROD") mailTo.joinToString (",") else testEmail,
                        if (testEmail == "PROD")  cc?.joinToString(",") else testEmail,
                        EventTypeEnum.EMAIL,
                        priority = 1,
                        NotificationStatusEnum.NEW,
                        failedReason = null,
                    ),
                ),
                headers,
            )
            val result: ResponseEntity<String> = restTemplate.postForEntity(uri, request, String::class.java)
            if (result.statusCode == HttpStatus.BAD_REQUEST) {
                log.error("Error while sending email notification")
                log.error(result.body)
                val failedReason = result.body?.take(4096)
            }
            return result.statusCode == HttpStatus.OK
        } catch (e: Exception) {
            log.error("Error while sending email notification")
            log.error(e.message)
            val failedReason = e.message?.take(4096)
            return false
        }
    }
}
