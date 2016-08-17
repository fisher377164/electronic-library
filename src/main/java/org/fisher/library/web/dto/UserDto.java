package org.fisher.library.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fisher.library.domain.entity.User;

import javax.validation.constraints.NotNull;

/**
 * @author fisher
 * @since 8/5/16.
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotNull(message = "error.userName.notnull")
    private String username;

    @NotNull(message = "error.password.notnull")
    private String password;

    @NotNull(message = "error.userFirstName.notnull")
    private String firstName;

    @NotNull(message = "error.userSecondName.notnull")
    private String lastName;

    @NotNull(message = "error.email.notnull")
    private String email;

    private byte[] avatar;

    public UserDto() {
    }

    public UserDto(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
    }

}
