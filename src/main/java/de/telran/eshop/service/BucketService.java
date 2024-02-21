package de.telran.eshop.service;

import de.telran.eshop.dto.BucketDTO;
import de.telran.eshop.entity.Bucket;
import de.telran.eshop.entity.User;

import java.util.List;

/**
 * Интерфейс, определяющий методы для работы с корзиной покупок.
 */
public interface BucketService {

    /**
     * Создает новую корзину для указанного пользователя и добавляет в неё товары.
     *
     * @param user       пользователь, для которого создается корзина
     * @param productIds список идентификаторов товаров, которые необходимо добавить в корзину
     * @return созданная корзина
     */
    Bucket createBucked(User user, List<Long> productIds);

    /**
     * Добавляет товары в существующую корзину.
     *
     * @param bucket     корзина, в которую добавляются товары
     * @param productIds список идентификаторов товаров, которые необходимо добавить
     */
    void addProducts(Bucket bucket, List<Long> productIds);

    /**
     * Получает корзину по имени пользователя.
     *
     * @param name имя пользователя
     * @return корзина пользователя в формате DTO
     */
    BucketDTO getBucketByUser(String name);

    /**
     * Удаляет товар из корзины покупок указанного пользователя.
     *
     * @param userId    идентификатор пользователя
     * @param productId идентификатор товара, который необходимо удалить
     * @return обновленная корзина после удаления товара
     */
    void removeProductFromBucket(Long userId, Long productId);
}
