package de.telran.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object), представляющий информацию о продукте.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    /**
     * Идентификатор продукта.
     */
    private Long id;

    /**
     * Наименование продукта.
     */
    private String title;

    /**
     * Цена продукта.
     */
    private BigDecimal price;
}
