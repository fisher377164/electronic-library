package org.fisher.library.domain.repository;


import org.fisher.library.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fisher
 * @since 8/4/16.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByEmailOrUsername(String email,String username);

}
