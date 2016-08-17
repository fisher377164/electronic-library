package org.fisher.library.security;

import org.fisher.library.domain.entity.User;
import org.fisher.library.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author fisher
 * @since 8/5/16.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

    private final UserRepository userRepository;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Inject
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public final User loadUserByUsername(String username) {
        LOGGER.info("userDetailsService");

        final User user = userRepository.findOneByEmailOrUsername(username, username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the DB");
        }
        detailsChecker.check(user);
        return user;
    }
}
