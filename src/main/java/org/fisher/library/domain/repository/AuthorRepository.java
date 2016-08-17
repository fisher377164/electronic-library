package org.fisher.library.domain.repository;

import org.fisher.library.domain.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fisher
 * @since 8/11/16.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
