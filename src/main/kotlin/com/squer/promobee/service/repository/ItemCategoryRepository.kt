package com.squer.promobee.service.repository

import com.squer.promobee.mapper.ItemCategoryMasterMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.ItemCategoryMaster
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ItemCategoryRepository(
        securityUtility: SecurityUtility
) : BaseRepository<ItemCategoryMaster>(
        securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    fun getItemCategory(): List<ItemCategoryMaster>{
        return sqlSessionFactory.openSession().selectList("ItemCategoryMasterMapper.getItemCategory")
    }

    fun getItemCategoryById(id: String): ItemCategoryMaster{
        return sqlSessionFactory.openSession().selectOne("ItemCategoryMasterMapper.getItemCategoryById", id)
    }
}