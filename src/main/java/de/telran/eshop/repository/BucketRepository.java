package de.telran.eshop.repository;

import de.telran.eshop.dto.BucketDTO;
import de.telran.eshop.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    @Query(nativeQuery = true, value = "SELECT * FROM buckets_products WHERE  product_id = :product_id")
    Bucket getByProductId(@Param(value = "product_id") Long productId);



}
