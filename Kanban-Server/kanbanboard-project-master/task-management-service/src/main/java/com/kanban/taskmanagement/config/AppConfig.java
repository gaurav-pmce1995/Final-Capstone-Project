package com.kanban.taskmanagement.config;

import com.kanban.taskmanagement.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> filterUrl () {
        FilterRegistrationBean<JwtFilter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new JwtFilter());

        frb.addUrlPatterns("/api/v1/kanbans/*");

        return frb;
    }
}
