package com.ecommerce.enquiry;

import com.ecommerce.util.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/enquiry")
public class EnquiryController {

    private final EnquiryService enquiryService;
    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<EnquiryDTO> createEnquiry(
            @RequestBody EnquiryDTO EnquiryDTO
    ){
        return new ResponseEntity<>(
                enquiryService.createEnquiry(EnquiryDTO),
                HttpStatus.CREATED
        );
    }
    @PutMapping("{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<EnquiryDTO> updateEnquiry(
            @PathVariable Long id,
            @RequestBody EnquiryDTO EnquiryDTO
    ){
        return ResponseEntity.ok(
                enquiryService.updateEnquiry(id, EnquiryDTO)
        );
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteEnquiry(
            @PathVariable Long id
    ){
        enquiryService.deleteEnquiry(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<EnquiryDTO> getEnquiry(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                enquiryService.getEnquiry(id)
        );
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<EnquiryDTO>> getEnquiries(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<EnquiryDTO> enquiries = enquiryService.findEnquiries(paginationDTO);
        return ResponseEntity.ok(enquiries);
    }
}
