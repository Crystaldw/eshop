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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order implements Serializable {

    private static final String SEQ_NAME = "order_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @CreationTimestamp //дата создания заказа будет записываться в это поле
    private LocalDateTime created;

    @UpdateTimestamp //обновление заказа (чтобы автомвтом все было)
    private LocalDateTime updated;

    @ManyToOne //множество заказов к одному пользователю
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal sum;

    private String address;

    //у одного заказа может быть много деталей
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetails> details;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;



}
