package de.telran.eshop.service;

import de.telran.eshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO>getAll();

    void addToUserBucket(Long productId, String username);
}
