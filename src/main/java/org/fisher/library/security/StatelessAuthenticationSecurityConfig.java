package org.fisher.library.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fisher.library.domain.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author fisher
 * @since 8/5/16.
 */
@EnableWebSecurity
@Configuration
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final TokenAuthenticationService tokenAuthenticationService;

    private final AuthenticationEntryPoint customAuthenticationEntryPoint;

    private final ObjectMapper jacksonObjectMapper;

    private final ResponseUserInfoService responseUserInfoService;

    @Autowired
    public StatelessAuthenticationSecurityConfig(TokenAuthenticationService tokenAuthenticationService,
                                                 AuthenticationEntryPoint customAuthenticationEntryPoint,
                                                 ObjectMapper jacksonObjectMapper,
                                                 UserDetailsService userDetailsService,
                                                 ResponseUserInfoService responseUserInfoService) {
        super(true); // Disable defaults
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.userDetailsService = userDetailsService;
        this.responseUserInfoService = responseUserInfoService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**/*.{js,html}")
                .antMatchers("/bower_components/**")
                .antMatchers("/i18n/**")
                .antMatchers("/assets/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .servletApi().and()

                .exceptionHandling().and()

                // Make Spring Security stateless. No session will be created by Spring Security, nor will it use any
                // previously existing session.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .anonymous().and()

                // Disable the CSRF prevention because it requires the session, which of course is not available in a
                // stateless application. It also greatly complicates the requirements for the sign in POST request.
                .csrf().disable()

                .headers().frameOptions().disable().and()

                .headers().cacheControl().and().and()

                .authorizeRequests()

                // allow all static resources
                .antMatchers("/favicon.ico",
                        "/scripts/**",
                        "/bower_components/**",
                        "/assets/**").permitAll()

                .antMatchers("/**/template/cabinet*", "/**/template/cabinet/**").authenticated()

                // allow anonymous GETs to index and other client pages
                .antMatchers(HttpMethod.GET,
                        "/", "index.html",
                        "/403*", "/404*", "/done*", "/about*"
                ).permitAll()

                // allow anonymous GETs for email confirmation
                .antMatchers(HttpMethod.GET,
                        "/api/user/current",
                        "/api/book/getBest/*",
                        "/api/book/getAll/*/*",
                        "/api/book/getAllCount"
                ).permitAll()

                // allow anonymous POSTs to username and sign up
                .antMatchers(HttpMethod.POST,
                        "/api/login",
                        "/api/user/check-username",
                        "/api/user/check-email",
                        "/api/user/registration"
                ).permitAll()

                .antMatchers("/api/admin/**").permitAll()

                .antMatchers("/api/**").authenticated()

                .antMatchers("/metrics/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/health/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/trace/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/dump/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/shutdown/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/beans/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/configprops/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/info/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/autoconfig/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/env/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/trace/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/mappings/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/liquibase/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/webjars/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/api-docs/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/v2/api-docs/**").hasRole(Authority.ADMIN.name())
                .antMatchers("/swagger-ui/index.html").hasRole(Authority.ADMIN.name())
                .antMatchers("/protected/**").authenticated()

                .and()

                // custom JSON based username filter which sets the token header upon authentication
                .addFilterBefore(
                        new StatelessUsernamePasswordAuthenticationFilter(
                                "/api/login",
                                tokenAuthenticationService,
                                responseUserInfoService,
                                userDetailsService,
                                authenticationManager(),
                                jacksonObjectMapper,
                                customAuthenticationEntryPoint
                        ),
                        UsernamePasswordAuthenticationFilter.class)

                // custom token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessTokenAuthenticationFilter(tokenAuthenticationService, customAuthenticationEntryPoint),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set a custom user details service that reads the user credentials from an external data source. This will
        // only ever be used once within the Spring Security process, that is during the initial sign in. The
        // verification of all authenticated requests are stateless, that is it does not require access to any internal
        // or external state
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
