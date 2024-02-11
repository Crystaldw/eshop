package de.telran.eshop.repository;

import de.telran.eshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для доступа к данным о категориях продуктов в базе данных.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    /**
     * Метод для поиска категории по названию.
     *
     * @param title название категории
     * @return найденная категория
     */
    Category findByTitle(String title);

}
