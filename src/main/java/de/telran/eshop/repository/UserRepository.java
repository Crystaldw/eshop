package de.telran.eshop.repository;

import de.telran.eshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для доступа к данным о пользователях в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Метод для поиска пользователя по имени.
     *
     * @param name имя пользователя
     * @return найденный пользователь
     */
    User findFirstByName(String name);
    void deleteById(User id);
}
