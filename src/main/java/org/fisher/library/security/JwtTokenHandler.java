package org.fisher.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.fisher.library.domain.entity.Authority;
import org.fisher.library.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fisher
 * @since 8/5/16.
 */

public class JwtTokenHandler implements TokenHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenHandler.class);

    private static final String CLAIM_ROLES = "roles";

    private final byte[] secret;

    public JwtTokenHandler(byte[] secret) {
        this.secret = secret;
    }

    @Override
    public User parseUserFromToken(final String token) {

        Claims body;
        try {
            body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            LOGGER.error("ExpiredJwtException " + e.getMessage());
            throw new ExpiredTokenException(e.getMessage());
        }
        User user = new User();
        user.setUsername(body.getSubject());
        user.setExpires(body.getExpiration().getTime());

        if (new Date().getTime() > user.getExpires()) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) body.get(CLAIM_ROLES);

        roles.stream().map(role -> {
            user.grantRole(Authority.valueOf(role));
            return user;
        });

        LOGGER.debug("parseUserFromToken: " + user);

        return user;
    }

    @Override
    public String createTokenForUser(final User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, user.getUsername());
        claims.put(CLAIM_ROLES, user.getRoles()); // TODO

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(user.getExpires())) // TODO
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
