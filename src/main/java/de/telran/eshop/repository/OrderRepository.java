package de.telran.eshop.repository;

import de.telran.eshop.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для доступа к данным о заказах в базе данных.
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {


}
