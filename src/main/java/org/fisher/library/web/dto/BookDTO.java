package org.fisher.library.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.internal.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fisher.library.domain.entity.Book;

/**
 * @author fisher
 * @since 8/11/16.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {

    private Long id;

    @NotNull
    private String bookName;

    private Long authorId;

    private String publishingHouse;

    @NotNull
    private String description;

    @NotNull
    private byte[] book;

    private byte[] bookImage;

    private int rating;

    public BookDTO() {
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.publishingHouse = book.getPublishingHouse();
        this.description = book.getBookName();
        this.bookImage = book.getBookImage();
        this.rating = book.getRating();
        this.book = book.getBook();

        if (book.getAuthor() != null)
            this.authorId = book.getAuthor().getId();
    }
}
