package com.squer.promobee.controller



import com.squer.promobee.security.domain.User
import com.squer.promobee.service.RevalidationService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@Slf4j
open class RevalidationController@Autowired constructor(
    private val revalidationService: RevalidationService
) {

    private val log = LoggerFactory.getLogger(javaClass)


    @GetMapping("/getItemRevalidationHub/{itemId}/{revldType}")
    fun getItemRevalidationHub(@PathVariable itemId: String,  @PathVariable revldType:String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = revalidationService.getItemRevalidationHub(itemId,  revldType)
        return ResponseEntity(data, HttpStatus.OK)
    }







}