package org.fisher.library.exeptions;

import org.springframework.http.HttpStatus;

/**
 * @author fisher
 * @since 9/20/16.
 */
public class IllegalPageException extends BaseException {

    public IllegalPageException(Integer page) {
        super(HttpStatus.BAD_REQUEST, "No Such Page Exception: " + page);
    }
}
