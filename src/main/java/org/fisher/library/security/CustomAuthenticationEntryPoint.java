package org.fisher.library.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fisher
 * @since 8/5/16.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    //TODO !!! remove comments
//    @Autowired
//    private ObjectMapper mapper;
//
//    @Autowired
//    private MessageSource messageSource;

//    @Autowired
//    private LocaleResolver localeResolver;

//    private Locale locale = new Locale("ru");

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.setCharacterEncoding("UTF-8");

//        User account is locked class org.springframework.security.authentication.InternalAuthenticationServiceException

//        this.locale = localeResolver.resolveLocale(request);

//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        if (e instanceof InternalAuthenticationServiceException) {
//            e = (AuthenticationException) e.getCause();
//        }
//
//        if (e instanceof BadCredentialsException) {
//            writeJson(response, StatusCode.BED_CREDENTIALS);
//        } else if (e instanceof ExpiredTokenException) {
//            writeJson(response, StatusCode.AUTH_TOKEN_IS_OUT_OF_DATE);
//        } else if (e instanceof LockedException) {
//            writeJson(response, StatusCode.USER_NOT_CONFIRMED);
//        } else if (e instanceof DisabledException) {
//            writeJson(response, StatusCode.USER_IS_DISABLED);
//        } else if (e instanceof UsernameNotFoundException) {
//            writeJson(response, StatusCode.NO_SUCH_USER);
//        } else {
//            writeJson(response, e.getMessage());
//        }
    }
//
//    private void writeJson(HttpServletResponse response, StatusCode statusCode) throws IOException {
//
//        String message = messageSource.getMessage(statusCode.getReasonPhrase(), null, this.locale);
//
//        statusCode.setMessage(message);
//        response.getOutputStream().write(mapper.writeValueAsString(statusCode).getBytes("UTF-8"));
//    }
//
//    private void writeJson(HttpServletResponse response, String message) throws IOException {
//        response.getOutputStream().write(mapper.writeValueAsString(message).getBytes("UTF-8"));
//    }
}
