package org.fisher.library.web;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fisher
 * @since 8/10/16.
 */

@Controller
public class WebConfig {
    private final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @RequestMapping(value = {
            "/",
            "/about",
            "/admin",
            "/login",
            "/register",
            "/all-books/*",
            "/all-books"
    }, method = RequestMethod.GET)
    public void pageForward(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        System.out.println("httpRequest.getRequestURI() " + httpRequest.getRequestURI());
        forward(httpRequest, httpResponse);
    }

    private void forward(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("index.html");
        try {
            dispatcher.forward(httpRequest, httpResponse);
        } catch (Exception e) {
            log.error("Error forwarding request", e);
        }
    }

    @Bean
    public CustomizationBean customizationBean() {
        return new CustomizationBean();
    }

    static class CustomizationBean implements EmbeddedServletContainerCustomizer {

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
        }

    }
}