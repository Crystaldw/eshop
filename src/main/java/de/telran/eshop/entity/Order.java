package de.telran.eshop.entity;

import de.telran.eshop.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сущность, представляющая заказ в системе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order implements Serializable {

    private static final String SEQ_NAME = "order_seq";

    /**
     * Уникальный идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    /**
     * Дата и время создания заказа.
     */
    @CreationTimestamp
    private LocalDateTime created;

    /**
     * Дата и время последнего обновления заказа.
     */
    @UpdateTimestamp
    private LocalDateTime updated;

    /**
     * Пользователь, сделавший заказ.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Общая сумма заказа.
     */
    private BigDecimal sum;

    /**
     * Адрес доставки заказа.
     */
    private String address;

    /**
     * Список деталей заказа.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetails> details;

    /**
     * Статус заказа.
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
