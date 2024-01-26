package de.telran.eshop.service.impl;

import de.telran.eshop.dto.ProductDTO;
import de.telran.eshop.mapper.ProductMapper;
import de.telran.eshop.repository.ProductRepository;
import de.telran.eshop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper = ProductMapper.MAPPER;

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }
}
