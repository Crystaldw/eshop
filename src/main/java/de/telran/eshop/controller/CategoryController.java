package de.telran.eshop.controller;

import de.telran.eshop.entity.Category;
import de.telran.eshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    @PostMapping("/create")
    public String createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return "success";
    }

    @GetMapping
    public List<Category> listCategory(){
        return categoryService.listCategory();
    }



}
