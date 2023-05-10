package com.squer.promobee.controller



import com.squer.promobee.security.domain.User
import com.squer.promobee.service.DashboardService
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
open class DashboardController@Autowired constructor(
    private val dashboardService: DashboardService
) {

    private val log = LoggerFactory.getLogger(javaClass)


    @GetMapping("/getPendingDispatch")
    open fun getPendingDispatch() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data = dashboardService.getPendingDispatch()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getHubNearExpiry")
    open fun getHubNearExpiry() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data = dashboardService.getHubNearExpiry()
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getHubPendingRevalidation")
    open fun getHubPendingRevalidation() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data = dashboardService.getHubPendingRevalidation()
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getHubGrnErrorLog")
    open fun getHubGrnErrorLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data = dashboardService.getHubGrnErrorLog()
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getItemExpiredDetails")
    open fun getItemExpiredDetails() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data = dashboardService.getItemExpiredDetails()
        return ResponseEntity(data, HttpStatus.OK)

    }







}