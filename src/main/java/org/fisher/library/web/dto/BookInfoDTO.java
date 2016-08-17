package org.fisher.library.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fisher.library.domain.entity.Book;

/**
 * @author fisher
 * @since 8/10/16.
 */


@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookInfoDTO {

    private Long id;

    private String bookName;

    private Long authorId;

    private String publishingHouse;

    private String description;

    private byte[] bookImage;

    private int rating;

    public BookInfoDTO() {
    }

    public BookInfoDTO(Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.publishingHouse = book.getPublishingHouse();
        this.description = book.getBookName();
        this.bookImage = book.getBookImage();
        this.rating = book.getRating();

        if (book.getAuthor() != null)
            this.authorId = book.getAuthor().getId();
    }
}
