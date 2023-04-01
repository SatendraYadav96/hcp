package com.squer.promobee.security.config.impl

import com.squer.promobee.security.config.SecurityConstants.APP_ACTUATOR_ENDPOINTS
import com.squer.promobee.security.config.SecurityConstants.APP_ACTUATOR_HEALTH_ENDPOINTS
import com.squer.promobee.security.config.SecurityConstants.H2_URL
import com.squer.promobee.security.config.SecurityConstants.SWAGGER_ENDPOINTS
import com.squer.promobee.security.config.SecurityConstants.V1_ENDPOINTS
import com.squer.promobee.security.config.SecurityConstants.V1_LOGS_ENDPOINTS
import com.squer.promobee.security.config.SecurityConstants.V1_SIGN_UP_ENDPOINTS
import com.squer.promobee.security.config.SecurityConstants.WEB_CONTENTS
import com.squer.promobee.security.config.SecurityService
import com.squer.promobee.security.filter.JwtAuthenticationFilter
import com.squer.promobee.security.jwt.JwtAuthenticationEntryPoint
import com.squer.promobee.security.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Service


@Service
class SecurityServiceImpl @Autowired constructor(
    private val authenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val customUserDetailsService: CustomUserDetailsService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) : SecurityService {

    @Throws(Exception::class)
    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.userDetailsService<UserDetailsService>(customUserDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
    }

    @Throws(Exception::class)
    override fun httpDisableCorsAndCSRF(http: HttpSecurity) {
        http.cors().and().csrf().disable()
    }

    @Throws(Exception::class)
    override fun httpAuthenticationEntryPointForException(http: HttpSecurity) {
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
    }

    @Throws(Exception::class)
    override fun httpAuthenticateAnyRequestForAuthorization(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().authenticated()
    }

    override fun httpAddJwtAuthFilterBeforeUsernamePasswordAuthenticationFilter(http: HttpSecurity) {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    override fun httpStatelessSessionCreationPolicy(http: HttpSecurity) {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Throws(Exception::class)
    override fun httpSameOriginFrameOptionForHeaders(http: HttpSecurity) {
        http.headers().frameOptions().sameOrigin()
    }

    override fun httpAuthorizeSwaggerUrlForAll(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*SWAGGER_ENDPOINTS).permitAll()
    }

    override fun httpAuthorizeWebContentsForAll(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*WEB_CONTENTS).permitAll()
    }

    override fun httpAuthorizeH2UrlForAll(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*H2_URL).permitAll()
    }

    override fun httpAuthorizeV1SignUpEndpointsForAll(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*V1_SIGN_UP_ENDPOINTS).permitAll()
    }

    override fun httpAuthorizeV1Endpoints(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*V1_ENDPOINTS).hasAnyAuthority("ADMIN", "DEVICE_USER")
    }

    @Throws(java.lang.Exception::class)
    override fun httpAuthorizeV1LogsEndpointsForAdmin(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*V1_LOGS_ENDPOINTS).hasAuthority("ADMIN")
    }

    override fun httpAuthorizeAppActuatorHealthForAll(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*APP_ACTUATOR_HEALTH_ENDPOINTS).permitAll()
    }

    override fun httpAuthorizeAppActuatorForAdmin(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*APP_ACTUATOR_ENDPOINTS).hasAuthority("ADMIN")
    }
}
