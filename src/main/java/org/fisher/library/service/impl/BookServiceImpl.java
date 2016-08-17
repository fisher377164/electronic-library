package org.fisher.library.service.impl;

import org.fisher.library.domain.entity.Author;
import org.fisher.library.domain.entity.Book;
import org.fisher.library.domain.repository.AuthorRepository;
import org.fisher.library.domain.repository.BookRepository;
import org.fisher.library.service.BookService;
import org.fisher.library.web.dto.BookDTO;
import org.fisher.library.web.dto.BookInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fisher
 * @since 8/10/16.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookInfoDTO> getTopBooks(Integer count) {
        PageRequest request = new PageRequest(0, count, Sort.Direction.DESC, "rating");
        Page<Book> bestBooksPage = bookRepository.findAll(request);
        List<BookInfoDTO> books = new ArrayList<>();
        bestBooksPage.forEach(book -> books.add(new BookInfoDTO(book)));
        return books;
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = new Book();

        book.setBookName(bookDTO.getBookName());
        book.setBook(bookDTO.getBook());
        book.setDescription(bookDTO.getDescription());
        book.setPublishingHouse(bookDTO.getPublishingHouse());
        book.setBookImage(bookDTO.getBookImage());
        book.setRating(bookDTO.getRating());
        if (bookDTO.getAuthorId() != null) {
            Author authorFromDB = authorRepository.findOne(bookDTO.getAuthorId());
            if (authorFromDB != null) {
                book.setAuthor(authorFromDB);
            }
        }
        Book bookFromDB = bookRepository.save(book);

        return new BookDTO(bookFromDB);
    }
}
