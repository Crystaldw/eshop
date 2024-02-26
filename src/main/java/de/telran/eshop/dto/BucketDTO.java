package de.telran.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO (Data Transfer Object), представляющий корзину покупок.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDTO {

    private Long userId;

    /**
     * Количество товаров в корзине.
     */
    private int amountProduct;

    /**
     * Сумма покупок в корзине.
     */
    private Double sum;

    /**
     * Список товаров корзины покупок.
     */
    private List<BucketDetailDTO> bucketDetails = new ArrayList<>();

    /**
     * Метод для агрегации суммы добавленных в корзину товаров.
     */
    public void aggregate(){
        // Определение количества товаров в корзине
        this.amountProduct = bucketDetails.size();

        // Вычисление общей суммы покупок в корзине
        this.sum = bucketDetails.stream()
                .map(BucketDetailDTO::getSum) // Получение суммы каждой детали корзины
                .mapToDouble(Double::doubleValue) // Преобразование в Double
                .sum(); // Суммирование
    }
}
