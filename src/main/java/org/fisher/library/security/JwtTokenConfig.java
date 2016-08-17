package org.fisher.library.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.DatatypeConverter;

/**
 * @author fisher
 * @since 8/5/16.
 */

@Configuration
public class JwtTokenConfig {

    @Bean
    public TokenHandler tokenHandler(@Value("${token.jwt.secret}") String secret) {
        return new JwtTokenHandler(DatatypeConverter.parseBase64Binary(secret));
    }
}