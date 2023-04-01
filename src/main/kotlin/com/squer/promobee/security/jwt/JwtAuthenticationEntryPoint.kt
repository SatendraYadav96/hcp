package com.squer.promobee.security.jwt

import com.google.gson.Gson
import com.squer.promobee.security.dto.LoginFailResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        e: AuthenticationException
    ) {
        val loginResponse = LoginFailResponse()
        val jsonLoginResponse = Gson().toJson(loginResponse)

        httpServletResponse.contentType = "application/json"
        httpServletResponse.status = HttpStatus.UNAUTHORIZED.value()
        httpServletResponse.writer.print(jsonLoginResponse)
    }
}
