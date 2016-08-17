package org.fisher.library.security;

import org.fisher.library.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fisher
 * @since 8/5/16.
 */
@Service
public class ResponseUserInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUserInfoService.class);

    void addUserInfo(HttpServletResponse response, User user) {
        LOGGER.info("addUserInfo: ");
        try {
            ServletOutputStream stream = response.getOutputStream();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            stream.print(getUserNameToString(user));
        } catch (IOException e) {
            LOGGER.info("IOException: " + e.getMessage());
        }
    }

    private String getUserNameToString(User user) {
        return "{\"username\":\"" +
                user.getUsername() + "\"}";
    }


}
