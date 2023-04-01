package com.squer.promobee.service


import com.squer.promobee.security.domain.User


interface UserService {
    fun saveUser(newUser: User): User
    fun updateUser(user: User)
    fun findByUsername(username: String): User
    fun restore(id: String): User

    fun findById(id:String): User

}
