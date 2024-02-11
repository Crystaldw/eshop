package de.telran.eshop.controller;

import de.telran.eshop.dto.ProductDTO;
import de.telran.eshop.service.ProductService;
import de.telran.eshop.service.SessionObjectHolder;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * Контроллер для управления запросами, связанными с продуктами.
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;

    /**
     * Конструктор контроллера.
     * @param productService Сервис продуктов для взаимодействия с продуктовой базой данных.
     * @param sessionObjectHolder Объект, хранящий информацию о сессии пользователя.
     */
    public ProductController(ProductService productService, SessionObjectHolder sessionObjectHolder) {
        this.productService = productService;
        this.sessionObjectHolder = sessionObjectHolder;
    }

    /**
     * Метод для отображения страницы добавления нового продукта.
     * Доступен только для пользователей с ролью "ADMIN".
     * @param model Модель, используемая для передачи данных на представление.
     * @return Имя представления для страницы добавления нового продукта.
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // доступ к данному методу только администратору
    @GetMapping("/new")
    public String addProduct(Model model) {
        System.out.println("Вызван метод добавления продуктов"); // для нас
        model.addAttribute("product", new ProductDTO());
        return "product";
    }

    /**
     * Метод для сохранения нового продукта.
     * Пользователь должен быть аутентифицирован и имя пользователя должно совпадать с именем пользователя в параметре метода.
     * @param productDTO DTO нового продукта.
     * @param model Модель, используемая для передачи данных на представление.
     * @return Перенаправление на страницу со списком продуктов, если продукт успешно сохранен, иначе возврат на страницу добавления продукта.
     */
    @PostAuthorize("isAuthenticated() and #username==authentication.principal.username")
    @PostMapping("/new")
    public String saveProduct(ProductDTO productDTO, Model model) {
        System.out.println("Вызван метод сохранения продукта");
        boolean saved = productService.save(productDTO);
        System.out.println("Продукт сохранен: " + saved); // Добавляем это сообщение для отладки
        if (saved) {
            return "redirect:/products";
        } else {
            model.addAttribute("product", productDTO);
            return "product";
        }
    }

    /**
     * Метод для отображения списка продуктов.
     * Добавляет количество кликов в сессии пользователя и список всех продуктов в модель для отображения.
     * @param model Модель, используемая для передачи данных на представление.
     * @return Имя представления для страницы со списком продуктов.
     */
    @GetMapping
    public String productList(Model model) {
        sessionObjectHolder.addClicks();
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }

    /**
     * Метод для добавления продукта в корзину пользователя.
     * Добавляет количество кликов в сессии пользователя и проверяет аутентификацию пользователя.
     * Если пользователь не аутентифицирован, он будет перенаправлен на страницу со списком продуктов.
     * @param id Идентификатор продукта для добавления в корзину.
     * @param principal Интерфейс для доступа к информации о текущем пользователе.
     * @return Перенаправление на страницу со списком продуктов.
     */
    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
        sessionObjectHolder.addClicks();
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }
}
