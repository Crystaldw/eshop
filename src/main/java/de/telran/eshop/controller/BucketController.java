package de.telran.eshop.controller;

import de.telran.eshop.dto.BucketDTO;
import de.telran.eshop.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Контроллер, обрабатывающий запросы, связанные с корзиной покупок.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;

    /**
     * Отображает информацию о корзине пользователя.
     */
    @GetMapping
    public String aboutBucket(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("bucket", new BucketDTO());
        } else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }
        return "bucket";
    }

    @DeleteMapping("/remove/{productId}")
    @PostAuthorize("isAuthenticated() and #userId==authentication.principal.username")
    public String removeProductFromBucket(@PathVariable (value = "userId")Long userId,
                                          @PathVariable(value = "productId")Long productId){
        bucketService.removeProductFromBucket(productId);
        return "redirect:/bucket";
    }

}
