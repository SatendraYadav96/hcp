package com.squer.promobee.service.repository


import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Users
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ApprovalRepository(
    securityUtility: SecurityUtility
): BaseRepository<Users>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory












}