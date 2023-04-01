package com.squer.promobee.security.filter

import com.squer.promobee.security.config.SecurityConstants.HEADER_STRING
import com.squer.promobee.security.config.SecurityConstants.TOKEN_PREFIX
import com.squer.promobee.security.jwt.JwtTokenProvider
import com.squer.promobee.security.service.CustomUserDetailsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class JwtAuthenticationFilter @Autowired constructor(
    private val tokenProvider: JwtTokenProvider,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(javaClass)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJWTFromRequest(httpServletRequest)
            if (SecurityContextHolder.getContext().authentication == null) {
                validateAndAddUserForAuthentication(httpServletRequest, jwt)
            }
        } catch (ex: Exception) {
            log.error("Could not set user authentication in security context", ex)
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    private fun validateAndAddUserForAuthentication(httpServletRequest: HttpServletRequest, jwt: String?) {
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            val userId = tokenProvider.getUserIdFromJWT(jwt)
            val userDetails = customUserDetailsService.loadUserById(userId)
            setAuthentication(httpServletRequest, userDetails)
        }
    }

    private fun setAuthentication(httpServletRequest: HttpServletRequest, userDetails: UserDetails?) {
        if (userDetails != null) {
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }

    private fun getJWTFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HEADER_STRING)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            bearerToken.substring(TOKEN_PREFIX.length)
        } else null
    }

}
