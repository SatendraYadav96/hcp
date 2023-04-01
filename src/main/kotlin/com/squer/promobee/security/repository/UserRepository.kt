package com.squer.promobee.security.repository

import com.squer.promobee.mapper.UserMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    securityUtility: SecurityUtility
): BaseRepository<User>(
    securityUtility= securityUtility,
) {
    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun findById(id: String) : User {
        return sqlSessionFactory.openSession().selectOne("UserMapper.findUserById", id)
    }

    fun findByUsername(username: String): User {
        return sqlSessionFactory.openSession().selectOne("UserMapper.findByUsername", username)
    }
    fun update(user: User) {
        sqlSessionFactory.openSession().update("UserMapper.updateUser", user)
    }
}
