package com.squer.promobee.security.config

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity

interface SecurityService {

    @Throws(Exception::class)
    fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder)

    @Throws(Exception::class)
    fun httpDisableCorsAndCSRF(http: HttpSecurity)

    @Throws(Exception::class)
    fun httpAuthenticationEntryPointForException(http: HttpSecurity)

    @Throws(Exception::class)
    fun httpAuthenticateAnyRequestForAuthorization(http: HttpSecurity)

    fun httpAddJwtAuthFilterBeforeUsernamePasswordAuthenticationFilter(http: HttpSecurity)

    @Throws(Exception::class)
    fun httpStatelessSessionCreationPolicy(http: HttpSecurity)

    @Throws(Exception::class)
    fun httpSameOriginFrameOptionForHeaders(http: HttpSecurity)

    @Throws(java.lang.Exception::class)
    fun httpAuthorizeSwaggerUrlForAll(http: HttpSecurity)

    @Throws(java.lang.Exception::class)
    fun httpAuthorizeWebContentsForAll(http: HttpSecurity)

    @Throws(java.lang.Exception::class)
    fun httpAuthorizeH2UrlForAll(http: HttpSecurity)

    @Throws(java.lang.Exception::class)
    fun httpAuthorizeV1SignUpEndpointsForAll(http: HttpSecurity)

    @Throws(java.lang.Exception::class)
    fun httpAuthorizeV1Endpoints(http: HttpSecurity)

    @Throws(java.lang.Exception::class)
    fun httpAuthorizeV1LogsEndpointsForAdmin(http: HttpSecurity)

    @Throws(java.lang.Exception::class)
    fun httpAuthorizeAppActuatorHealthForAll(http: HttpSecurity)

    @Throws(java.lang.Exception::class)
    fun httpAuthorizeAppActuatorForAdmin(http: HttpSecurity)

}
