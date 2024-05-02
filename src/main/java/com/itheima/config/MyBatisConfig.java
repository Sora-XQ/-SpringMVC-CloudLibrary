package com.itheima.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

public class MyBatisConfig {
    //配置分页插件
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("value", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
    /**
     * 定义Mybatis的核心链接工厂Bean
     * 等同于<bean class="org.mybatis.spring.SqlSessionFactoryBean"/>
     * 参数使用自动装配的形式加载dataSource,
     * 为set注入提供数据源,dataSource来源于JdbcConfig中的配置
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(
            @Autowired DataSource dataSource,
            @Autowired PageInterceptor pageInterceptor){
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        //等同于<property name="dataSource" ref="dataSource"/>
        ssfb.setDataSource(dataSource);
        Interceptor[] plugins = {pageInterceptor};
        ssfb.setPlugins(plugins);
        return ssfb;
    }
    /**
     * 定义MyBatis的映射扫描
     * 等同于<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer"></bean>
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        //等同于<property name="basePackage" value="com.itheima.dao"/>
        msc.setBasePackage("com.itheima.mapper");
        return msc;
    }
}