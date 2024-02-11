package de.telran.eshop.service;

import de.telran.eshop.dto.ProductDTO;

import java.util.List;

/**
 * Интерфейс, предоставляющий функциональность для работы с продуктами.
 */
public interface ProductService {

    /**
     * Получает список всех продуктов.
     *
     * @return список всех продуктов в формате DTO
     */
    List<ProductDTO> getAll();

    /**
     * Сохраняет новый продукт.
     *
     * @param productDTO информация о продукте в формате DTO
     * @return true, если продукт успешно сохранен, в противном случае - false
     */
    boolean save(ProductDTO productDTO);

    /**
     * Добавляет указанный продукт в корзину пользователя.
     *
     * @param productId идентификатор добавляемого продукта
     * @param username  имя пользователя, чья корзина будет обновлена
     */
    void addToUserBucket(Long productId, String username);
}
