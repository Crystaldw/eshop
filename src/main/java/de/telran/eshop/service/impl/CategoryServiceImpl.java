package de.telran.eshop.service.impl;

import de.telran.eshop.entity.Category;
import de.telran.eshop.repository.CategoryRepository;
import de.telran.eshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
    @Override
    public List<Category> listCategory() {
        return (List<Category>) categoryRepository.findAll();
    }
}
