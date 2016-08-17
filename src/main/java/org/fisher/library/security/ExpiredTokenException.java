package org.fisher.library.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author fisher
 * @since 8/5/16.
 */
public class ExpiredTokenException extends AuthenticationException {

    public ExpiredTokenException(String message) {
        super(message);
    }

}
