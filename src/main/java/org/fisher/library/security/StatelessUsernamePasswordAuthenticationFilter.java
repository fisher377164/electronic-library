package org.fisher.library.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fisher.library.domain.entity.User;
import org.fisher.library.web.dto.UserLoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fisher
 * @since 8/5/16.
 */
public class StatelessUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatelessUsernamePasswordAuthenticationFilter.class);

    private final TokenAuthenticationService tokenAuthenticationService;
    private final ResponseUserInfoService responseUserInfoService;
    private final org.springframework.security.core.userdetails.UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;

    public StatelessUsernamePasswordAuthenticationFilter(String urlMapping,
                                                         TokenAuthenticationService tokenAuthenticationService,
                                                         ResponseUserInfoService responseUserInfoService,
                                                         org.springframework.security.core.userdetails.UserDetailsService userDetailsService,
                                                         AuthenticationManager authManager,
                                                         ObjectMapper objectMapper,
                                                         AuthenticationEntryPoint customAuthenticationEntryPoint) {
        super(new AntPathRequestMatcher(urlMapping));
        this.responseUserInfoService = responseUserInfoService;
        this.userDetailsService = userDetailsService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.objectMapper = objectMapper;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        final UserLoginDto user = objectMapper.readValue(request.getInputStream(), UserLoginDto.class);

        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            LOGGER.warn("Attempt to authenticate with empty credentials: {}", user);
            throw new BadCredentialsException("Attempt to authenticate user with empty credentials.");
        }

        LOGGER.info("attemptAuthentication" + user.toString());

        try {
            UsernamePasswordAuthenticationToken
                    loginToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword());
            return getAuthenticationManager().authenticate(loginToken);
        } catch (AuthenticationException e) {
            customAuthenticationEntryPoint.commence(request, response, e);
            LOGGER.info("attemptAuthentication AuthenticationException {}", e);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        // Lookup the complete User object from the database and create an Authentication for it
        // TODO: check this: attemptAuthentication() has already found this user (print sql)

        LOGGER.info("successfulAuthentication");
        try {
            final User authenticatedUser = (User) userDetailsService.loadUserByUsername(authentication.getName());

            UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);


            // Add the custom token as HTTP header to the response
            tokenAuthenticationService.addAuthentication(response, request, userAuthentication);
            LOGGER.debug("addAuthentication");
            responseUserInfoService.addUserInfo(response, authenticatedUser);
            // Add the authentication to the Security context
            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        } catch (AuthenticationException e) {
            LOGGER.warn("Unexpected exception: {}", e.getMessage(), e);
            customAuthenticationEntryPoint.commence(request, response, e);
        }
    }
}