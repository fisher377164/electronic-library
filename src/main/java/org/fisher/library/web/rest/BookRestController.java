package org.fisher.library.web.rest;

import org.fisher.library.service.BookService;
import org.fisher.library.web.dto.BookDTO;
import org.fisher.library.web.dto.BookInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author fisher
 * @since 8/10/16.
 */

@RestController
@RequestMapping("/api/book")
public class BookRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/getBest/{count}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTopBooks(@PathVariable("count") Integer count) {

        LOGGER.info("getTopBestBooks count: " + count);

        List<BookInfoDTO> topBooks = bookService.getTopBooks(count);
        return new ResponseEntity<>(topBooks, HttpStatus.OK);
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addBook(@Valid @RequestBody BookDTO bookDTO) {

        LOGGER.info("addBook bookDTO: " + bookDTO);

        BookDTO bookFromBD = bookService.addBook(bookDTO);
        return new ResponseEntity<>(bookFromBD, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getAll/{count}/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllBooksPage(@PathVariable("page") Integer page, @PathVariable("count") Integer count) {

        LOGGER.info("getAllBooksPage page: " + page + " count: " +count);

        List<BookInfoDTO> allBooksPage = bookService.getAllBooksPage(page, count);
        return new ResponseEntity<>(allBooksPage, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllBooksCount() {

        LOGGER.info("getAllBooksCount");

        long allBooksCount = bookService.getAllBooksCount();

        return new ResponseEntity<>(allBooksCount, HttpStatus.OK);
    }




}
