package com.tidyup.StockService.Service;

import com.tidyup.StockService.Repository.BrandRepository;
import com.tidyup.StockService.domain.product.dto.BrandDTO;
import com.tidyup.StockService.domain.product.dto.DetailedBrandDTO;
import com.tidyup.StockService.domain.product.entity.Brand;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public DetailedBrandDTO create(@Valid BrandDTO brandDTO) {
        var brandEntity = brandRepository.save(new Brand(brandDTO));
        return new DetailedBrandDTO(brandEntity);
    }

    public Page<DetailedBrandDTO> getAll(Pageable pageable) {
        return brandRepository.findAll(pageable).map(DetailedBrandDTO::new);
    }

    public DetailedBrandDTO getById(Long id) {
        return brandRepository.findById(id).map(DetailedBrandDTO::new).orElseThrow(EntityNotFoundException::new);
    }

    public DetailedBrandDTO update(Long id, @Valid DetailedBrandDTO detailedBrandDTO) {
        Brand brand = brandRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        brand.update(detailedBrandDTO);
        return new DetailedBrandDTO(brand);
    }

    public void delete(Long id) {
        brandRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        brandRepository.deleteById(id);
    }
}
