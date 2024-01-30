package de.telran.eshop.repository;

import de.telran.eshop.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findFirstByName(String name);
}
