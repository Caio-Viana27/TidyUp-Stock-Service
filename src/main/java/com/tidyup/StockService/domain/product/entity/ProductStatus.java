package com.tidyup.StockService.domain.product.entity;

import com.tidyup.StockService.domain.product.dto.DetailedProductStatusDTO;
import com.tidyup.StockService.domain.product.dto.ProductStatusDTO;
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
@Table(name = "PRODUCT_STATUS")
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS")
    private StatusValue status;

    public ProductStatus(ProductStatusDTO dto) {
        this.status = dto.status();
    }

    public ProductStatus(DetailedProductStatusDTO dto) {
        this.id = dto.id();
        this.status = dto.status();
    }

    public void update(ProductStatusDTO dto) {
        /*StatusValue value = StatusValue.valueOf(dto.status().name());*/
        this.status = dto.status();
    }

    public boolean equals(DetailedProductStatusDTO dto) {
        return this.id.equals(dto.id()) && this.status.equals(dto.status());
    }
}
