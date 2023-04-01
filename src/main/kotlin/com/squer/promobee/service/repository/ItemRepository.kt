package com.squer.promobee.service.repository

import com.squer.promobee.mapper.ItemMapper
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.Item
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ItemRepository (
        securityUtility: SecurityUtility
        ): BaseRepository<Item>(
        securityUtility = securityUtility
        ) {

        @Autowired
        lateinit var sqlSessionFactory: SqlSessionFactory
        

        fun getMaxItemCode(code: String): String{
            return sqlSessionFactory.openSession().selectOne("ItemMapper.getMaxItemCode", code)
            //return itemMapper.getMaxItemCode(code)["count"] as String
        }

        fun getItemDataById(id: String): Item{
            return sqlSessionFactory.openSession().selectOne("ItemMapper.getItemDataById", id)
        }

        fun getItemDataByCode(code: String): Item{
            return sqlSessionFactory.openSession().selectOne("ItemMapper.getItemDataByCode", code)
        }

        fun insertItem(item: Item){
            sqlSessionFactory.openSession().insert("ItemMapper.insertItem", item)
        }
}