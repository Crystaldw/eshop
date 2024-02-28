package de.telran.eshop.service.impl;

import de.telran.eshop.entity.Order;
import de.telran.eshop.repository.OrderRepository;
import de.telran.eshop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для работы с заказами.
 */

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    /**
     * Сохраняет заказ в базе данных.
     *
     * @param order сохраняемый заказ
     */
    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order saveOrder = orderRepository.save(order);
        orderRepository.save(saveOrder);
    }

    /**
     * Получает заказ по его идентификатору.
     *
     * @param id идентификатор заказа
     * @return заказ, если найден, в противном случае null
     */
    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


}
