package com.kanban.userprofileservice.config;

import com.kanban.userprofileservice.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> filterUrl() {
        FilterRegistrationBean<JwtFilter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new JwtFilter());

        frb.addUrlPatterns("/api/v1/user-profile/get-user",
                "/api/v1/user-profile/update-user",
                "/api/v1/user-profile/delete",
                "/api/v1/user-profile/upload");

        return frb;
    }
}
