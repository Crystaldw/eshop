package de.telran.eshop.service;

import de.telran.eshop.entity.Order;

/**
 * Интерфейс сервиса для работы с заказами.
 */
public interface OrderService {

    /**
     * Сохраняет заказ в системе.
     *
     * @param order заказ для сохранения
     */
    void saveOrder(Order order);

    /**
     * Получает заказ по его идентификатору.
     *
     * @param id идентификатор заказа
     * @return заказ, если найден, в противном случае null
     */
    Order getOrder(Long id);
}
