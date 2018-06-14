package com.eric.config;

import com.eric.common.utils.BaseDao;
import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * 通用Mapper以及PageHelper配置
 *
 * @author Chen 2018/1/9
 */
@Configuration
public class MapperConfig {
    /**
     * 通用Mapper扫描配置
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.eric.modules.sys.dao");//扫描modules下的dao
        Properties properties = new Properties();
        properties.setProperty("mappers","com.eric.common.utils.BaseDao");//通用dao
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

    /**
     * PageHelper分页工具
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("helper-dialect","mysql");//mysql方言
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}