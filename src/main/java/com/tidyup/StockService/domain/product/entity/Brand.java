package com.tidyup.StockService.domain.product.entity;

import com.tidyup.StockService.domain.product.dto.BrandDTO;
import com.tidyup.StockService.domain.product.dto.DetailedBrandDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BRANDS")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RETAILER_ID")
    private UUID retailerId;

    @Column(name = "BRAND")
    private String brand;

    public Brand(BrandDTO dto) {
        this.retailerId = dto.retailerId();
        this.brand = dto.brand();
    }

    public Brand(DetailedBrandDTO dto) {
        this.retailerId = dto.retailerId();
        this.brand = dto.brand();
    }

    public void update(@Valid DetailedBrandDTO detailedBrandDTO) {
        this.retailerId = detailedBrandDTO.retailerId();
        this.brand = detailedBrandDTO.brand();
    }

    public boolean equals(DetailedBrandDTO dto) {
        return this.id.compareTo(dto.id()) == 0 && this.retailerId.compareTo(dto.retailerId()) == 0 && this.brand.equals(dto.brand());
    }
}
