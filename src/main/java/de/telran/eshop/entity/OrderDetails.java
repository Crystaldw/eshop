package de.telran.eshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Сущность, представляющая детали заказа в системе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders_details")
public class OrderDetails {

    private static final String SEQ_NAME = "order_details_seq";

    /**
     * Уникальный идентификатор детали заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    /**
     * Заказ, к которому относится данная деталь.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Товар, связанный с данной деталью заказа.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Количество товара в детали заказа.
     */
    private BigDecimal amount;

    /**
     * Цена товара в заказа.
     */
    private BigDecimal price;
}
