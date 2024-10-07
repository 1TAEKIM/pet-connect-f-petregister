package com.dogmeat.petconnect.petregister.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.dogmeat.petconnect.petregister.mapper.PetConnectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class PetConnectDatasource {

    @Value("${petconnect.datasource.url}")
    private String url;

    @Value("${petconnect.datasource.username}")
    private String username;

    @Value("${petconnect.datasource.password}")
    private String password;

    @Bean
    DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setIdleTimeout(600_000);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

    @Bean
    SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations(new ClassPathResource("mapper/mapper.xml"));
        return factoryBean.getObject();
    }

    @Bean
    SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    PetConnectMapper petConnectMapper() throws Exception {
        return sqlSessionTemplate().getMapper(PetConnectMapper.class);
    }

}
