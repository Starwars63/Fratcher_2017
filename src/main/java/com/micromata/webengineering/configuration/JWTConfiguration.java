package com.micromata.webengineering.configuration;

import com.micromata.webengineering.aya.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfiguration {
    @Autowired
    public AuthenticationService authenticationService;

    @Autowired
    public UserService userService;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new JWTFilter(authenticationService, userService));
        bean.addUrlPatterns("/api/*");
        return bean;
    }
}