package com.tidyup.StockService.domain.product.entity;

import com.tidyup.StockService.domain.product.dto.CreateProductDTO;
import com.tidyup.StockService.domain.product.dto.UpdateProductDTO;
import com.tidyup.StockService.infrastructure.exception.InvalidNewProductInventoryValueException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @Column(name = "ID")
    private final UUID id = UUID.randomUUID();

    @Column(name = "RETAILER_ID")
    private UUID retailerId;

    @Column(name = "SKU")
    private String SKU;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "INVENTORY")
    private Integer inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRAND_ID")
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "PRODUCT_CATEGORY", joinColumns = { @JoinColumn(name = "PRODUCT_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "CATEGORY_ID") })
    private List<ProductCategory> productCategoryList;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public Product(CreateProductDTO dto) {
        this.retailerId = dto.retailerId();
        this.SKU = dto.SKU();
        this.name = dto.name();
        this.description = dto.description();
        this.price = dto.price();
        this.inventory = dto.inventory();
        this.status = new ProductStatus(dto.status());
        this.brand = new Brand(dto.brand());
        this.productCategoryList = dto.categoriesList().stream().map(ProductCategory::new).toList();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void update(UpdateProductDTO data) {
        this.SKU = data.SKU();
        this.name = data.name();
        this.description = data.description();
        this.price = data.price();
        this.inventory = data.inventory();
        this.status = new ProductStatus(data.status());
        this.productCategoryList = data.categoryList().stream().map(ProductCategory::new).toList();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(ProductStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void update(List<ProductCategory> newCategoryList) {
        this.productCategoryList = newCategoryList;
        this.updatedAt = LocalDateTime.now();
    }

    public void setNewInventory(int requestedAmount) {
        int newValue = this.inventory - requestedAmount;
        if (newValue < 0)
            throw new InvalidNewProductInventoryValueException("Product inventory must not be less than zero", this.id,
                    this.inventory, requestedAmount);
        this.inventory = newValue;
    }
}
