package de.telran.eshop.service;

import de.telran.eshop.entity.Order;

public interface OrderService {

    void saveOrder(Order order);

    Order getOrder(Long id);
}
