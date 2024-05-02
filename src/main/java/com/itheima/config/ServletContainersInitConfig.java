package com.itheima.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletContainersInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 加载Spring配置类中的信息
     * 初始化Spring容器
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /**
     * 加载Spring Mvc配置类中的信息
     * 初始化Spring Mvc容器
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    /**
     * 配置DispatcherServlet的映射路劲
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
