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
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] book;

    private String bookName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Comment> comment;

    private String publishingHouse;

    private String description;

    @Lob
    private byte[] bookImage;

    private Integer rating;

    @OneToMany(mappedBy = "book")
    private List<FavoriteBook> favoriteByUsers;


}
