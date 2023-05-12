package com.squer.promobee.security.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.squer.promobee.service.repository.domain.LegalEntity
import com.squer.promobee.service.repository.domain.UserDesignation
import com.squer.promobee.service.repository.domain.UserStatus

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date
import javax.validation.constraints.NotBlank
import kotlin.collections.HashSet

class   User : java.io.Serializable, UserDetails, AuditableEntity() {

    var name: String? = null
    var ciName: String? = null

    private var username: @NotBlank(message = "Please enter username") String? = null

    private var password: String? = "\$2a\$12\$xY9m7YCnR.63zf9KGv22ieKyFlkai6K/ANoGN6AH.rL3WdjpY/mou"

    var employeeCode: String? = null

    var userDesignation: NamedSquerEntity? = null

    var activeFrom: Date? = null

    var activeTo: Date?  = null

    var legalEntity: NamedSquerEntity?  = null

    var email: String?  =null

    var lastLoggedIn: Date? =  null

    var userStatus: NamedSquerEntity?= null

    var roles: List<SecurityRole>? = mutableListOf<SecurityRole>()



    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableSet<GrantedAuthority> = HashSet()
        authorities.add(SimpleGrantedAuthority("ADMIN"))
        return authorities
    }

    override fun getUsername(): String? = username

    @JsonIgnore
    @JsonProperty(value = "password")
    override fun getPassword(): String? = password

    fun setPassword(value: String?) {
        password = value
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean = true

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean = true

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean = true

    @JsonIgnore
    override fun isEnabled(): Boolean = true

}
