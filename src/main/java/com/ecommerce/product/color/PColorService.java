package com.ecommerce.product.color;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PColorService {

    private final PColorRepository pColorRepository;
    private final PColorMapper pColorMapper;

    public PColorDTO createColor(PColorDTO pColorDTO) {
        PColor pColor = pColorMapper.toEntity(pColorDTO);
        return pColorMapper.toDto(
                pColorRepository.save(pColor)
        );
    }
    private PColor findPColorById(Long id){
        PColor pColor = pColorRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product color with id [%s] not found".formatted(id))
                );
        return pColor;
    }

    public PColorDTO updateProductColor(Long id, PColorDTO pColorDTO) {
        PColor pColor = findPColorById(id);
        pColor.setName(pColorDTO.name());
        return pColorMapper.toDto(
                pColorRepository.save(pColor)
        );
    }

    public void deleteProductColor(Long id) {
        PColor color = findPColorById(id);
        pColorRepository.delete(color);
    }

    public PColorDTO getProductCategory(Long id) {
        PColor pColor = findPColorById(id);
        return pColorMapper.toDto(pColor);
    }
}
