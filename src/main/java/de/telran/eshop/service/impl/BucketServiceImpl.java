package de.telran.eshop.service.impl;

import de.telran.eshop.dto.BucketDTO;
import de.telran.eshop.dto.BucketDetailDTO;
import de.telran.eshop.entity.*;
import de.telran.eshop.entity.enums.OrderStatus;
import de.telran.eshop.repository.BucketRepository;
import de.telran.eshop.repository.ProductRepository;
import de.telran.eshop.service.BucketService;
import de.telran.eshop.service.OrderService;
import de.telran.eshop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
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
    private final OrderService orderService;

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
        User user = userService.findByName(name); // Находим пользователя по его имени
        if (user == null || user.getBucket() == null) { // Если пользователь не найден или у него нет корзины, возвращаем пустую корзину в формате DTO
            return new BucketDTO();
        }

        BucketDTO bucketDTO = new BucketDTO(); // Создаем объект DTO для корзины пользователя
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>(); // Создаем отображение (Map), чтобы группировать товары по их идентификатору

        List<Product> products = user.getBucket().getProducts(); // Получаем список товаров из корзины пользователя
        for (Product product : products) { // Обходим каждый товар в корзине
            BucketDetailDTO detail = mapByProductId.get(product.getId()); // Получаем детали товара по его идентификатору из отображения
            if (detail == null) { // Если деталей товара нет в отображении, добавляем новые
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal("1.0")));  // Если детали уже существуют, увеличиваем количество товара на 1 и обновляем сумму
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }

        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values())); // Устанавливаем детали корзины из отображения в DTO объект
        bucketDTO.aggregate(); // Выполняем агрегацию деталей корзины

        return bucketDTO; // Возвращаем заполненную корзину в формате DTO
    }

    /**
     * Удаляет товар из корзины покупок указанного пользователя.
     * <p>
     * //     * @param userId    идентификатор пользователя
     *
     * @param productId идентификатор товара, который необходимо удалить
     * @return обновленная корзина после удаления товара
     */
//    public void removeProductFromBucket(Long productId, Long userId) {
//        Bucket bucketDelete = bucketRepository.getByProductIdAndUserId(productId, userId);
//        bucketRepository.delete(bucketDelete);
//    }

    /**
     * Удаляет товар из корзины покупок.
     *
     * @param productId идентификатор товара, который необходимо удалить
     */
    @Override
    public void removeProductFromBucket(Long productId) {
        Bucket bucketDeleteProduct = bucketRepository.getByProductId(productId);
        bucketRepository.delete(bucketDeleteProduct);
    }

    /**
     * Формирует заказ на основе содержимого корзины покупок указанного пользователя и сохраняет его в системе.
     * Если корзина пуста или пользователь не найден, метод завершает свою работу без создания заказа.
     *
     * @param username имя пользователя, для которого формируется заказ
     */
    @Override
    @Transactional
    public void commitBucketToOrder(String username) {
        User user = userService.findByName(username); // Находим пользователя по его имени
        if (user == null) {  // Если пользователь не найден, выбрасываем исключение
            throw new RuntimeException("User is not found");
        }
        Bucket bucket = user.getBucket(); // Получаем корзину пользователя
        if (bucket == null || bucket.getProducts().isEmpty()) {  // Если корзина пуста или не существует, завершаем метод
            return;
        }

        Order order = new Order(); // Создаем новый заказ и устанавливаем его статус и пользователя
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()  // Группируем товары в корзине по количеству
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream() // Создаем список деталей заказа на основе товаров в корзине
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .toList();

        BigDecimal total = new BigDecimal(orderDetails.stream() // Вычисляем общую сумму заказа
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails); // Устанавливаем детали и сумму заказа, а также адрес (в данном случае неуказанный)
        order.setSum(total);
        order.setAddress("none");

        orderService.saveOrder(order); // Сохраняем заказ в системе
        bucket.getProducts().clear(); // Очищаем содержимое корзины и сохраняем ее в системе
        bucketRepository.save(bucket);
    }
}





