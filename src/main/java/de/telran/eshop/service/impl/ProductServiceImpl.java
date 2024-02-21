package de.telran.eshop.service.impl;

import de.telran.eshop.dto.ProductDTO;
import de.telran.eshop.entity.Bucket;
import de.telran.eshop.entity.Product;
import de.telran.eshop.entity.User;
import de.telran.eshop.entity.enums.Role;
import de.telran.eshop.mapper.ProductMapper;
import de.telran.eshop.repository.ProductRepository;
import de.telran.eshop.service.BucketService;
import de.telran.eshop.service.ProductService;
import de.telran.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Реализация сервиса для управления продуктами.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper = ProductMapper.MAPPER;

    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    /**
     * Получает список всех продуктов.
     *
     * @return список всех продуктов в формате DTO
     */
    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList((List<Product>) productRepository.findAll());
    }

    /**
     * Сохраняет новый продукт.
     *
     * @param productDTO информация о продукте в формате DTO
     * @return true, если продукт успешно сохранен, в противном случае - false
     * @throws IllegalArgumentException если название продукта пустое или цена отрицательная
     */
    @Override
    public boolean save(ProductDTO productDTO) {
        if (productDTO.getTitle() == null || productDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть положительной");
        }
        Product product = Product.builder()
                .title(productDTO.getTitle())
                .price(productDTO.getPrice())
                .build();
        productRepository.save(product);
        return true;
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Добавляет указанный продукт в корзину пользователя.
     *
     * @param productId идентификатор добавляемого продукта
     * @param username  имя пользователя, чья корзина будет обновлена
     * @throws RuntimeException если пользователь не найден
     */
    @Override
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("Пользователь не найден - " + username);
        }
        Bucket bucket = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucked(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        } else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }
}
