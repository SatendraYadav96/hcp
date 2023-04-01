package com.squer.promobee.persistence

import com.squer.promobee.security.domain.NamedSquerId
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedTypes
import org.apache.ibatis.type.TypeHandler
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource


@Configuration
@MapperScan("com.squer.promobee.mapper")
class PersistenceConfig {

    @Value("\${spring.datasource.url}")
    lateinit var dbUrl: String

    @Value("\${spring.datasource.username}")
    lateinit var dbUsername: String

    @Value("\${spring.datasource.password}")
    lateinit var dbPassword: String

    @Bean
    fun dataSource(): DataSource? {
        val dataSourceBuilder = DataSourceBuilder.create()
        //dataSourceBuilder.driverClassName("org.h2.Driver")
        dataSourceBuilder.url(dbUrl)
        dataSourceBuilder.username(dbUsername)
        dataSourceBuilder.password(dbPassword)
        return dataSourceBuilder.build()
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
