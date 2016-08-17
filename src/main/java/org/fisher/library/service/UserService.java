package org.fisher.library.service;

import org.fisher.library.web.dto.UserDto;

/**
 * @author fisher
 * @since 8/5/16.
 */
public interface UserService {

    boolean isUserNameExists(String userName);

    boolean isUserEmailExists(String email);

    UserDto register(UserDto user);
}
