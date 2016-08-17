package org.fisher.library.security;

import org.fisher.library.domain.entity.User;

/**
 * @author fisher
 * @since 8/5/16.
 */
public interface TokenHandler {

    User parseUserFromToken(String token);

    String createTokenForUser(User user);
}