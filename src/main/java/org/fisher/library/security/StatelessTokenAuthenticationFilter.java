package org.fisher.library.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fisher
 * @since 8/5/16.
 */
public class StatelessTokenAuthenticationFilter extends GenericFilterBean {
    private final TokenAuthenticationService tokenAuthenticationService;
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;

    public StatelessTokenAuthenticationFilter(TokenAuthenticationService taService,
                                              AuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.tokenAuthenticationService = taService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        try {

            SecurityContextHolder.getContext().setAuthentication(
                    tokenAuthenticationService.getAuthentication((HttpServletRequest) req));

        } catch (AuthenticationException e) {
            customAuthenticationEntryPoint.commence((HttpServletRequest) req, (HttpServletResponse) res, e);
        }

        chain.doFilter(req, res); // always continue

        SecurityContextHolder.clearContext();
    }
}
