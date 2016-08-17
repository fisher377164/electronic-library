package org.fisher.library.domain.repository;

import org.fisher.library.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fisher
 * @since 8/4/16.
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
