package org.fisher.library.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author fisher
 * @since 8/4/16.
 */

@Data
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String activationToken;

    private String resetToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
