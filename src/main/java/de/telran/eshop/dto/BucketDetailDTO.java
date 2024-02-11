package de.telran.eshop.dto;

import de.telran.eshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object), представляющий детали элемента корзины покупок.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailDTO {

    /**
     * Наименование товара.
     */
    private String title;

    /**
     * Идентификатор товара.
     */
    private Long productId;

    /**
     * Цена товара.
     */
    private BigDecimal price;

    /**
     * Количество товара.
     */
    private BigDecimal amount;

    /**
     * Сумма покупки (цена * количество).
     */
    private Double sum;

    /**
     * Конструктор класса на основе объекта Product.
     * Используется для удобства конвертации Product в BucketDetailDTO.
     * @param product Объект товара.
     */
    public BucketDetailDTO(Product product) {
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = new BigDecimal("1.0");  // Установим количество в 1
        this.sum = Double.valueOf(product.getPrice().toString());  // Преобразуем цену в double и установим как сумму
    }
}
