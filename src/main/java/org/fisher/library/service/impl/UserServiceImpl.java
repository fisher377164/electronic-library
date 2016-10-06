package org.fisher.library.service.impl;

import org.fisher.library.domain.entity.Authority;
import org.fisher.library.domain.entity.User;
import org.fisher.library.domain.repository.UserRepository;
import org.fisher.library.exeptions.IllegalPageException;
import org.fisher.library.service.UserService;
import org.fisher.library.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

/**
 * @author fisher
 * @since 8/5/16.
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String USERNAME_SELECTOR = "username";


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isUserNameExists(String userName) {
        return false;
    }

    @Override
    public boolean isUserEmailExists(String email) {
        return false;
    }

    @Override
    public UserDto register(UserDto userDto) {
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAvatar(userDto.getAvatar());
        user.grantRole(Authority.USER);
        user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

        User userFromDB = userRepository.save(user);

        return new UserDto(userFromDB);
    }

    @Override
    public long getAllUsersCount() {
        return userRepository.count();

    }

    @Override
    public List<UserDto> getAllUsers(Integer count, Integer page) throws IllegalPageException {
        Integer maxPage = (int) ceil(((double) getAllUsersCount()) / count);
        if (page <= 0 || page > maxPage) {
            throw new IllegalPageException(page);
        }
        PageRequest request = new PageRequest(page - 1, count, Sort.Direction.ASC, USERNAME_SELECTOR);
        Page<User> usersPage = userRepository.findAll(request);
        List<UserDto> users = new ArrayList<>();
        usersPage.forEach(user -> users.add(new UserDto(user)));
        return users;
    }
}
