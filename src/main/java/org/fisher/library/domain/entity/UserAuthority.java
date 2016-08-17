package org.fisher.library.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author fisher
 * @since 8/5/16.
 */
@Entity
@IdClass(UserAuthority.class)
@Table(name = "user_authorities")
@Setter
public class UserAuthority implements GrantedAuthority {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Id
    private User user;

    @NotNull
    @Id
    private String authority;

    public UserAuthority() {
    }

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuthority that = (UserAuthority) o;

        return user.equals(that.user) && authority.equals(that.authority);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + authority.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return authority;
    }
}
