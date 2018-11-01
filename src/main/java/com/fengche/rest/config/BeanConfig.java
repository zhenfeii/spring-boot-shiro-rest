package com.fengche.rest.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by  Administrator on  2018/11/1
 */
@Configuration
public class BeanConfig {

    @Bean(name = "properties")
    public Properties properties() throws Exception {

        InputStream in = BeanConfig.class.getClassLoader().getResourceAsStream("db.properties");

        Properties properties = new Properties();
        properties.load(in);

        return properties;
    }

    @Bean
    public DruidDataSource druidDataSource(Properties properties) {

        DruidDataSource druidDataSource = new DruidDataSource();

        //链接池属性
        druidDataSource.setUrl(properties.getProperty("url"));
        druidDataSource.setUsername(properties.getProperty("username"));
        druidDataSource.setPassword(properties.getProperty("password"));

        //setInitialSize
        druidDataSource.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));

        //setMaxActive
        druidDataSource.setMaxActive(Integer.parseInt(properties.getProperty("maxActive")));

        //setMinIdle
        druidDataSource.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));

        //setMaxWait*/
        druidDataSource.setMaxWait(Long.parseLong(properties.getProperty("maxWait"))) ;

        return druidDataSource;
    }

    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource druidDataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(druidDataSource);

        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:/mapping/*.xml");

        sqlSessionFactoryBean.setMapperLocations(resources);

        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();

        configurer.setBasePackage("com.fengche.rest.dao");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");

        return configurer;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DruidDataSource druidDataSource){

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(druidDataSource);

        return transactionManager;
    }

}
