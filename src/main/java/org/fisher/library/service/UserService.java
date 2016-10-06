package org.fisher.library.service;

import org.fisher.library.exeptions.IllegalPageException;
import org.fisher.library.web.dto.UserDto;

import java.util.List;

/**
 * @author fisher
 * @since 8/5/16.
 */
public interface UserService {

    boolean isUserNameExists(String userName);

    boolean isUserEmailExists(String email);

    UserDto register(UserDto user);

    long getAllUsersCount();

    List<UserDto> getAllUsers(Integer count, Integer page) throws IllegalPageException;
}
