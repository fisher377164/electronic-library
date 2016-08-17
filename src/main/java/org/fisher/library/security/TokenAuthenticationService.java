package org.fisher.library.security;

import org.fisher.library.config.CustomProperties;
import org.fisher.library.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fisher
 * @since 8/5/16.
 */

@Service
public class TokenAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);

    public final TokenHandler tokenHandler;

    public final CustomProperties jHipsterProperties;

    @Autowired
    public TokenAuthenticationService(CustomProperties jHipsterProperties, TokenHandler tokenHandler) {
        this.jHipsterProperties = jHipsterProperties;
        this.tokenHandler = tokenHandler;
    }

    void addAuthentication(HttpServletResponse response, HttpServletRequest request, UserAuthentication authentication) {
        final User user = authentication.getDetails();
        LOGGER.debug("addAuthentication");

        long expiration = jHipsterProperties.getSecurity().getToken().getExpiration();

        user.setExpires(getTime(expiration));

        response.addHeader(HttpHeaders.AUTHORIZATION, tokenHandler.createTokenForUser(user));
    }

    private long getTime(long expires) {
        return System.currentTimeMillis() + expires;
    }

    Authentication getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
            //throw new NotAuthorizedException("Authorization header must be provided");
        }

        final String token = authorizationHeader.substring("Bearer".length()).trim();
        User user = tokenHandler.parseUserFromToken(token);

        if (user != null) {
            return new UserAuthentication(user);
        }

        return null;
    }
}
