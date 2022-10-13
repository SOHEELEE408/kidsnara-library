package com.kidsnara.library.config.security;

import com.kidsnara.library.security.filter.ExceptionHandlerFilter;
import com.kidsnara.library.security.common.FilterSkipMatcher;
import com.kidsnara.library.security.filter.FormLoginFilter;
import com.kidsnara.library.security.filter.JwtAuthenticationFilter;
import com.kidsnara.library.security.handler.FormLoginAuthenticationSuccessHandler;
import com.kidsnara.library.security.handler.FormLoginAuthenticationFailureHandler;
import com.kidsnara.library.security.jwt.HeaderTokenExtractor;
import com.kidsnara.library.security.provider.FormLoginAuthenticationProvider;
import com.kidsnara.library.security.provider.JWTAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
    private final FormLoginAuthenticationFailureHandler formLoginAuthenticationFailureHandler;

    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final JWTAuthenticationProvider jwtProvider;
    private final HeaderTokenExtractor headerTokenExtractor;

    private final FormLoginAuthenticationProvider provider;

    protected FormLoginFilter formLoginFilter() throws Exception{
        FormLoginFilter filter = new FormLoginFilter(
                new AntPathRequestMatcher("/users/login", HttpMethod.POST.name()),
                formLoginAuthenticationSuccessHandler,
                formLoginAuthenticationFailureHandler
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(this.provider)
                .authenticationProvider(this.jwtProvider);
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
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(
                        formLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        exceptionHandlerFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        jwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        http
                .authorizeRequests()
                .mvcMatchers(
                        HttpMethod.POST,
                        "/books"
                )
                .hasRole("USER");
        http
                .cors(withDefaults());
    }

    private JwtAuthenticationFilter jwtFilter() throws Exception{
        List<AntPathRequestMatcher> skipPath = new ArrayList<>();

        skipPath.add(new AntPathRequestMatcher("/error", HttpMethod.GET.name()));
        skipPath.add(new AntPathRequestMatcher("/favicon.ico", HttpMethod.GET.name()));
        skipPath.add(new AntPathRequestMatcher("/static", HttpMethod.GET.name()));
        skipPath.add(new AntPathRequestMatcher("/static/**", HttpMethod.GET.name()));
        skipPath.add(new AntPathRequestMatcher("/books", HttpMethod.GET.name()));

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPath,
                "/**"
        );

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                matcher,
                headerTokenExtractor
        );

        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }
}
