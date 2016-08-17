package org.fisher.library;

import org.fisher.library.config.CustomProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author fisher
 * @since 8/4/16.
 */

@SpringBootApplication
@EnableConfigurationProperties({ CustomProperties.class })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
