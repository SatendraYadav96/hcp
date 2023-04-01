package com.squer.promobee.security.jwt

import com.squer.promobee.security.domain.User
import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtTokenProvider {

    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${app.security.JWT_SECRET_KEY}")
    private val JWT_SECRET_KEY: String? = null

    @Value(value = "\${app.security.JWT_TOKEN_EXPIRATION_TIME}")
    private var JWT_TOKEN_EXPIRATION_TIME: Long = 3600000L // default 1hr

    // Generate the token
    fun generateToken(authentication: Authentication): String {
        val user: User = authentication.principal as User
        val now = Date(System.currentTimeMillis())
        val expiryDate = Date(now.time + JWT_TOKEN_EXPIRATION_TIME)
        val userId = user.id.toString()
        val claims: MutableMap<String?, Any?> = HashMap()
        claims["id"] = user.id.toString()
        claims["username"] = user.username
        claims["name"] = user.name
        return Jwts.builder()
            .setSubject(userId)
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
            .compact()
    }

    // Validate the token
    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            log.error("Invalid JWT Signature")
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT Token")
        } catch (ex: ExpiredJwtException) {
            log.error("Expired JWT Token")
        } catch (ex: UnsupportedJwtException) {
            log.error("Unsupported JWT Token")
        } catch (ex: IllegalArgumentException) {
            log.error("JWT claims string is empty")
        }
        return false
    }

    // Get user id from token
    fun getUserIdFromJWT(token: String?): String {
        val claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).body
        val id = claims["id"] as String?
        return id!!
    }
}
