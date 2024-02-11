package de.telran.eshop.mapper;

import de.telran.eshop.dto.ProductDTO;
import de.telran.eshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Интерфейс, отвечающий за маппинг объектов между классами Product и ProductDTO.
 */
@Mapper
public interface ProductMapper {

    /**
     * Получение экземпляра маппера.
     */
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    /**
     * Метод преобразования ProductDTO в Product.
     */
    Product toProduct(ProductDTO dto);

    /**
     * Метод преобразования Product в ProductDTO с обратной конфигурацией.
     */
    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);

    /**
     * Метод для преобразования списка ProductDTO в список Product.
     */
    List<Product> toProductList(List<ProductDTO> productDTOS);

    /**
     * Метод для преобразования списка Product в список ProductDTO.
     */
    List<ProductDTO> fromProductList(List<Product> products);
}
