package com.zoulj.msapp.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author leonard
 * @date 2022/9/1
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }


    /**
     * 添加Web项目的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/**/login/**").excludePathPatterns("/**/configuration/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
}
