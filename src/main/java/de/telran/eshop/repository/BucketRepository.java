package de.telran.eshop.repository;

import de.telran.eshop.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для доступа к данным о корзине в базе данных.
 */
@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {

    /**
     * Метод для поиска корзины по идентификатору пользователя.
     *
     * @param userId идентификатор пользователя
     * @return корзина пользователя
     */
    Bucket findAllByUserId(Long userId);
}
