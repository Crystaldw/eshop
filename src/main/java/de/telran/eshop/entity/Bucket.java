package de.telran.eshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Сущность, представляющая корзину покупок в системе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "buckets")
public class Bucket {

    private static final String SEQ_NAME = "bucket_seq";

    /**
     * Уникальный идентификатор корзины.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    /**
     * Пользователь, которому принадлежит данная корзина.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Список товаров в корзине.
     */
    @ManyToMany
    @JoinTable(name = "buckets_products",
            joinColumns = @JoinColumn(name = "bucket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    /**
     * Дата создания корзины.
     */
    private Date createDate;
}
