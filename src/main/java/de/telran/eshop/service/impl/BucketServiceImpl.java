package de.telran.eshop.service.impl;

import de.telran.eshop.dto.BucketDTO;
import de.telran.eshop.dto.BucketDetailDTO;
import de.telran.eshop.entity.Bucket;
import de.telran.eshop.entity.Product;
import de.telran.eshop.entity.User;
import de.telran.eshop.repository.BucketRepository;
import de.telran.eshop.repository.ProductRepository;
import de.telran.eshop.service.BucketService;
import de.telran.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link BucketService}, предоставляющая функциональность работы с корзиной покупок.
 */
@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    /**
     * Создает новую корзину для указанного пользователя и добавляет в неё товары.
     *
     * @param user       пользователь, для которого создается корзина
     * @param productIds список идентификаторов товаров, которые необходимо добавить в корзину
     * @return созданная корзина
     */
    @Override
    public Bucket createBucked(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                //getOne вытягивает ссылку на обьект, findById - вытаскивает сам обьект
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    /**
     * Добавляет товары в существующую корзину.
     *
     * @param bucket     корзина, в которую добавляются товары
     * @param productIds список идентификаторов товаров, которые необходимо добавить
     */
    @Override
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);

    }

    /**
     * Получает корзину по имени пользователя.
     *
     * @param name имя пользователя
     * @return корзина пользователя в формате DTO
     */
    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO();
        }

        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }

        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }

    /**
     * Удаляет товар из корзины покупок указанного пользователя.
     *
     * @param userId    идентификатор пользователя
     * @param productId идентификатор товара, который необходимо удалить
     * @return обновленная корзина после удаления товара
     */
    @Override
    public Bucket removeProductFromCart(Long userId, Long productId) {
        return null;
    }

}
