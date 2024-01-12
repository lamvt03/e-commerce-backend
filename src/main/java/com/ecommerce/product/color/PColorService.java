package com.ecommerce.product.color;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.color.request.PColorCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PColorService {

    private final PColorRepository pColorRepository;
    private final PColorMapper pColorMapper;

    public PColorDTO createColor(PColorCreateRequest request) {
        PColor pColor = pColorMapper.toEntity(request);
        return pColorMapper.toDto(
                pColorRepository.save(pColor)
        );
    }
    private PColor findPColorById(Long id){
        return pColorRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product color with id [%s] not found".formatted(id))
                );
    }

    public PColorDTO updateProductColor(Long id, PColorCreateRequest request) {
        PColor pColor = findPColorById(id);
        return pColorMapper.toDto(
                pColorRepository.save(pColorMapper.toEntity(pColor, request))
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

    public List<PColorDTO> getAllProductCategories(){
        return pColorRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(pColorMapper::toDto)
                .toList();
    }
}
