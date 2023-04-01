package com.squer.promobee.controller

import com.squer.promobee.service.RecipientService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Slf4j
open class RecipientController @Autowired constructor(
        private val recipientService: RecipientService
){

    @GetMapping("/forteam/{teamId}")
    fun getRecipient(@PathVariable teamId: String): ResponseEntity<*>{
        val data = recipientService.getRecipients(teamId)
        return ResponseEntity(data, HttpStatus.OK)
    }
}