package com.squer.promobee.service.repository



import com.squer.promobee.controller.dto.ItemRevalidationDTO
import com.squer.promobee.controller.dto.RecipientReportDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.UploadLog
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class RevalidationRepository(
    securityUtility: SecurityUtility
): BaseRepository<UploadLog>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getItemRevalidationHub( itemId: String, revldType: String) : List<ItemRevalidationDTO>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("itemId", itemId)
        data.put("revldType", revldType)

        return sqlSessionFactory.openSession().selectList("InventoryExpiryUpdateMapper.getItemRevalidationHub", data)
    }













}