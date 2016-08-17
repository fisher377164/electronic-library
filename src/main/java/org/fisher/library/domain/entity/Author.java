package org.fisher.library.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author fisher
 * @since 8/4/16.
 */

@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String secondName;

    @Lob
    private byte[] authorImage;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

}
