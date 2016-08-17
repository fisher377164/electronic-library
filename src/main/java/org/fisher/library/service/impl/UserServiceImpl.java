package org.fisher.library.service.impl;

import org.fisher.library.domain.entity.Authority;
import org.fisher.library.domain.entity.User;
import org.fisher.library.domain.repository.UserRepository;
import org.fisher.library.service.UserService;
import org.fisher.library.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fisher
 * @since 8/5/16.
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

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

        User userFromDB = userRepository.save(user);

        return new UserDto(userFromDB);
    }
}
