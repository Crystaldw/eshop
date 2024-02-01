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

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;

    public ProductController(ProductService productService, SessionObjectHolder sessionObjectHolder) {
        this.productService = productService;
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") // доступ к данному методу только администратору
    @GetMapping("/new")
    public String addProduct(Model model) {
        System.out.println("called method addProducts"); // для нас
        model.addAttribute("product", new ProductDTO());
        return "product";
    }

    @PostAuthorize("isAuthenticated() and #username==authentication.principal.username")
    @PostMapping("/new")
    public String saveProduct(ProductDTO productDTO, Model model) {
        System.out.println("called method saveProduct");
        boolean saved = productService.save(productDTO);
        System.out.println("Product saved: " + saved); // Добавляем это сообщение для отладки
        if (saved) {
            return "redirect:/products";
        } else {
            model.addAttribute("product", productDTO);
            return "product";
        }
    }

    @GetMapping
    public String productList(Model model) {
        sessionObjectHolder.addClicks();
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }

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
