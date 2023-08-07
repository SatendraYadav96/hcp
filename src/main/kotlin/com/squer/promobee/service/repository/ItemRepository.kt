package com.squer.promobee.service.repository

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
            return sqlSessionFactory.openSession().selectOne<String?>("ItemMapper.getMaxItemCode", code).toString()
            //return itemMapper.getMaxItemCode(code)["count"] as String
        }

        fun getItemDataById(id: String): Item{
            var data : MutableMap<String, Any> = mutableMapOf()
            data.put("id", id)
            return sqlSessionFactory.openSession().selectOne("ItemMapper.getItemDataById", data)
        }

        fun getItemDataByCode(code: String):  List<Item> {
            var data : MutableMap<String, Any> = mutableMapOf()
            data.put("code", code)



          return sqlSessionFactory.openSession().selectList<Item>("ItemMapper.getItemDataByCode",data)



        }

        fun insertItem(item: Item){
            sqlSessionFactory.openSession().insert("ItemMapper.insertItem", item)
        }
}

fun <SqlSession> SqlSession.select(s: String, data: MutableMap<String, Any>) {

}
