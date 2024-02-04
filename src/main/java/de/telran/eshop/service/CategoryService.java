package de.telran.eshop.service;

import de.telran.eshop.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public interface CategoryService {

    public void createCategory(Category category);

    public List<Category> listCategory();
}
