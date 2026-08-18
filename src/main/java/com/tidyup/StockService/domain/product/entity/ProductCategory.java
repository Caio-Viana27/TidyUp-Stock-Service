package com.tidyup.StockService.domain.product.entity;

import com.tidyup.StockService.domain.product.dto.DetailedProductCategoryDTO;
import com.tidyup.StockService.domain.product.dto.ProductCategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORIES")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    public ProductCategory(ProductCategoryDTO dto) {
        this.category = dto.category();
    }

    public ProductCategory(DetailedProductCategoryDTO dto) {
        this.id = dto.id();
        this.category = dto.category();
    }

    public void update(ProductCategoryDTO productCategory) {
        this.category = productCategory.category();
    }

    public boolean equals(DetailedProductCategoryDTO dto) {
        return this.id.equals(dto.id()) && this.category.equals(dto.category());
    }
}
