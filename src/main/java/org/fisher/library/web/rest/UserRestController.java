package org.fisher.library.web.rest;

import org.fisher.library.security.UserAuthentication;
import org.fisher.library.service.UserService;
import org.fisher.library.web.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author fisher
 * @since 8/5/16.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthentication) {
            return ((UserAuthentication) authentication).getDetails().getUsername();
        }
        return authentication.getName(); //anonymous user support
    }

    @RequestMapping(value = "/check-username", method = RequestMethod.POST)
    public ResponseEntity checkUserName(@RequestBody String userName) {

        if (userService.isUserNameExists(userName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }

    }

    @RequestMapping(value = "/check-email", method = RequestMethod.POST)
    public ResponseEntity checkUserEmail(@RequestBody String email) {

        if (userService.isUserEmailExists(email)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }

    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUserWithReal(@Valid @RequestBody UserDto user) {

        LOGGER.info("registerUser /user/registration");

        UserDto registeredUser = userService.register(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);

    }
}
