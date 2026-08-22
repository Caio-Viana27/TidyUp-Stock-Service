package com.tidyup.StockService.service;

import com.tidyup.StockService.repository.BrandRepository;
import com.tidyup.StockService.repository.ProductCategoryRepository;
import com.tidyup.StockService.repository.ProductRepository;
import com.tidyup.StockService.repository.ProductStatusRepository;
import com.tidyup.StockService.domain.product.dto.*;
import com.tidyup.StockService.domain.product.entity.Brand;
import com.tidyup.StockService.domain.product.entity.Product;
import com.tidyup.StockService.domain.product.entity.ProductCategory;
import com.tidyup.StockService.domain.product.entity.ProductStatus;
import com.tidyup.StockService.infrastructure.exception.EntityDoesNotExistException;
import com.tidyup.StockService.infrastructure.exception.EntityDoesNotMatchException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductStatusRepository productStatusRepository;

    public DetailedProductDTO create(CreateProductDTO dto) {
        var product = new Product(dto);
        product.setBrand(validateBrand(dto.brand()));
        product.setProductCategoryList(validateProductCategoryList(dto.categoriesList()));
        product.setStatus(validateProductStatus(dto.status()));
        var productEntity = productRepository.save(product);
        return new DetailedProductDTO(productEntity);
    }

    public Page<SimpleProductDTO> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(SimpleProductDTO::new);
    }

    public SimpleProductDTO getById(UUID id) {
        return productRepository.findById(id).map(SimpleProductDTO::new).orElseThrow(EntityNotFoundException::new);
    }

    public DetailedProductDTO update(UUID id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.update(dto);
        return new DetailedProductDTO(product);
    }

    public DetailedProductDTO update(UUID id, DetailedProductStatusDTO dto) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ProductStatus productStatus = validateProductStatus(dto);
        product.update(productStatus);
        productRepository.saveAndFlush(product);
        return new DetailedProductDTO(product);
    }

    public DetailedProductDTO update(UUID id, List<DetailedProductCategoryDTO> dtoList) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<ProductCategory> entityList = validateProductCategoryList(dtoList);
        product.update(entityList);
        productRepository.saveAndFlush(product);
        return new DetailedProductDTO(product);
    }

    public void delete(UUID id) {
        productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        productRepository.deleteById(id);
    }

    private Brand validateBrand(DetailedBrandDTO dto) {
        Optional<Brand> brandOptional = brandRepository.findById(dto.id());

        if (brandOptional.isEmpty())
            throw new EntityDoesNotExistException("Brand with id: " + dto.id() + " doesn't exist!");
        Brand brandEntity = brandOptional.get();

        if (!brandEntity.equals(dto))
            throw new EntityDoesNotMatchException("Brand attributes don't match the fields of the Brand entity with the id: " + brandEntity.getId());
        return brandEntity;
    }

    private List<ProductCategory> validateProductCategoryList(List<DetailedProductCategoryDTO> list) {
        List<ProductCategory> validProductCategoryEntities = new ArrayList<>();

        list.forEach(productCategoryDTO -> {
            Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(productCategoryDTO.id());
            if (productCategoryOptional.isEmpty())
                throw new EntityDoesNotExistException("Category with id: " + productCategoryDTO.id() + " doesn't exists!");
            ProductCategory productCategoryEntity = productCategoryOptional.get();

            if (!productCategoryEntity.equals(productCategoryDTO))
                throw new EntityDoesNotMatchException("Category attributes don't match the fields of the Category entity with the Id: " + productCategoryEntity.getId());
            validProductCategoryEntities.add(productCategoryEntity);
        });

        return validProductCategoryEntities;
    }

    private ProductStatus validateProductStatus(DetailedProductStatusDTO dto) {
        Optional<ProductStatus> productStatusOptional = productStatusRepository.findById(dto.id());

        if (productStatusOptional.isEmpty())
            throw new EntityDoesNotExistException("Product Status with id: " + dto.id() + " doesn't exist!");
        ProductStatus productStatusEntity = productStatusOptional.get();

        if (!productStatusEntity.equals(dto))
            throw new EntityDoesNotMatchException("Product Status attributes don't match the fields of the Product Status entity with the id: " + productStatusEntity.getId());
        return productStatusEntity;
    }
}
