package com.example.demo;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@MapperScan(basePackages="com.example.demo.*.mapper")
@EnableTransactionManagement
public class DatabaseConfig {
 
    @Autowired
    GlobalPropertySource globalPropertySource;
    
    @Bean
    @Primary
    public DataSource customDataSource() {
    	System.out.println("getUrl : "+globalPropertySource.getUrl());
    	System.out.println("getDriverClassName : "+globalPropertySource.getDriverClassName());
    	System.out.println("getUsername : "+globalPropertySource.getUsername());
    	System.out.println("getPassword : "+globalPropertySource.getPassword());

    	return DataSourceBuilder
            .create()
            .url(globalPropertySource.getUrl())
            .driverClassName(globalPropertySource.getDriverClassName())
            .username(globalPropertySource.getUsername())
            .password(globalPropertySource.getPassword())
            .build();
    }
    
    @Bean
	public SqlSessionFactory db1SqlSessionFactory(ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(customDataSource());
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:publicMapper/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
      final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
      return sqlSessionTemplate;
    }
 
 
}

