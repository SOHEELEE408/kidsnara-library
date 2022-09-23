package com.kidsnara.library.config.security;

import com.kidsnara.library.security.filter.FormLoginFilter;
import com.kidsnara.library.security.handler.FormLoginAuthenticationSuccessHandler;
import com.kidsnara.library.security.handler.FormLoginAuthenticationFailureHandler;
import com.kidsnara.library.security.provider.FormLoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
    private final FormLoginAuthenticationFailureHandler formLoginAuthenticationFailureHandler;

    private final FormLoginAuthenticationProvider provider;

    protected FormLoginFilter formLoginFilter() throws Exception{
        FormLoginFilter filter = new FormLoginFilter(
                new AntPathRequestMatcher("/api/users/login", HttpMethod.POST.name()),
                formLoginAuthenticationSuccessHandler,
                formLoginAuthenticationFailureHandler
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(this.provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();
        http
                .headers()
                .frameOptions()
                .disable();

        http
                .addFilterBefore(
                        formLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );
    }
}
