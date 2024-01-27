package de.telran.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDTO {
    private int amountProduct;
    private Double sum;
    private List<BucketDetailDTO> bucketDetails = new ArrayList<>();

    //агренирует сумму добавленного в корзину товара
    public void aggregate(){
        this.amountProduct=bucketDetails.size();
        this.sum = bucketDetails.stream()
                .map(BucketDetailDTO :: getSum)
                .mapToDouble(Double::doubleValue)
                .sum();

    }
}
