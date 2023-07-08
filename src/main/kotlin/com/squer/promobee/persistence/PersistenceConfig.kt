package com.squer.promobee.persistence

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource


@Configuration
//@MapperScan("com.squer.promobee.mapper")
class PersistenceConfig {

    @Value("\${spring.datasource.url}")
    lateinit var dbUrl: String

    @Value("\${spring.datasource.username}")
    lateinit var dbUsername: String

    @Value("\${spring.datasource.password}")
    lateinit var dbPassword: String

    @Bean
    fun dataSource(): DataSource? {
        val pool = SQLServerConnectionPoolDataSource()
        pool.url = dbUrl
        pool.user = dbUsername
        //pool.authentication = dbPassword
        pool.setPassword(dbPassword)


        return pool
    }

    @Bean
    fun sqlSession(): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(): SqlSessionFactory? {
        val factoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(dataSource())
        factoryBean.setMapperLocations(*PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"))
        return factoryBean.getObject()
    }
}
