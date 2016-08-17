package org.fisher.library.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fisher
 * @since 8/5/16.
 */

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class UserLoginDto {

    private String username;

    private String password;

    public UserLoginDto() {
    }
}
