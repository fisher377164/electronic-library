package org.fisher.library.service;

import org.fisher.library.web.dto.BookDTO;
import org.fisher.library.web.dto.BookInfoDTO;

import java.util.List;

/**
 * @author fisher
 * @since 8/10/16.
 */
public interface BookService {

    List<BookInfoDTO> getTopBooks(Integer count);

    BookDTO addBook(BookDTO bookDTO);
}
