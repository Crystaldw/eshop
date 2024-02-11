package de.telran.eshop.repository;

import de.telran.eshop.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для доступа к данным о продуктах в базе данных.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    /**
     * Метод для получения продукта по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return найденный продукт
     */
    Product getOne(Long id);
}
