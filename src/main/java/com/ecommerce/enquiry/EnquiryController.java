package com.ecommerce.enquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/enquiry")
public class EnquiryController {

    private final EnquiryService enquiryService;
    @PostMapping
    public ResponseEntity<EnquiryDTO> createEnquiry(
            @RequestBody EnquiryDTO EnquiryDTO
    ){
        return new ResponseEntity<>(
                enquiryService.createEnquiry(EnquiryDTO),
                HttpStatus.CREATED
        );
    }
    @PutMapping("{id}")
    public ResponseEntity<EnquiryDTO> updateEnquiry(
            @PathVariable Long id,
            @RequestBody EnquiryDTO EnquiryDTO
    ){
        return ResponseEntity.ok(
                enquiryService.updateEnquiry(id, EnquiryDTO)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEnquiry(
            @PathVariable Long id
    ){
        enquiryService.deleteEnquiry(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EnquiryDTO> getEnquiry(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                enquiryService.getEnquiry(id)
        );
    }
}
