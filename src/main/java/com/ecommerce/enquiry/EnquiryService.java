package com.ecommerce.enquiry;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.util.PaginationService;
import com.ecommerce.util.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EnquiryService {

    private final EnquiryRepository enquiryRepository;
    private final EnquiryMapper enquiryMapper;

    private final PaginationService paginationService;

    public EnquiryDTO createEnquiry(EnquiryDTO enquiryDTO) {
        Enquiry enquiry = enquiryMapper.toEntity(enquiryDTO);
        return enquiryMapper.toDto(
                enquiryRepository.save(enquiry)
        );
    }
    private Enquiry findEnquiryById(Long id){
        return enquiryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Enquiry with id [%s] not found".formatted(id))
                );
    }

    public EnquiryDTO updateEnquiry(Long id, EnquiryDTO enquiryDTO) {
        Enquiry enquiry = findEnquiryById(id);
        enquiry.setName(enquiryDTO.name());
        enquiry.setMobile(enquiryDTO.mobile());
        enquiry.setEmail(enquiryDTO.email());
        enquiry.setComment(enquiryDTO.comment());
        return enquiryMapper.toDto(
                enquiryRepository.save(enquiry)
        );
    }

    public void deleteEnquiry(Long id) {
        Enquiry enquiry = findEnquiryById(id);
        enquiryRepository.delete(enquiry);
    }

    public EnquiryDTO getEnquiry(Long id) {
        Enquiry enquiry = findEnquiryById(id);
        return enquiryMapper.toDto(enquiry);
    }

    public List<EnquiryDTO> findEnquiries(PaginationDTO paginationDTO) {
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return enquiryRepository.findAll(pageable).stream()
                .map(enquiryMapper::toDto)
                .toList();
    }
}
