package com.ecommerce.enquiry;

import com.ecommerce.enquiry.request.EnquiryCreateRequest;
import com.ecommerce.exception.ErrResponse;
import com.ecommerce.util.model.PaginationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/enquiry")
@Tag(name = "Enquiry Controller")
public class EnquiryController {

    private final EnquiryService enquiryService;

    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EnquiryCreateRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"name\":\"Vo Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"comment\":\"This website has a beautiful and flexible appearance\"}")
                    }
            )
    )
    @Operation(
            summary = "Create new enquiry",
            description = "This endpoint is specifically guest to create new enquiry"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Create successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnquiryDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"name\":\"Vo Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"comment\":\"This website has a beautiful and flexible appearance\",\"createdAt\":\"2024-01-12T00:53:55.1621267\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "409", description = "Duplicate Resource",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"Enquiry with email [truonglam.113.147@gmail.com] already exists\",\"code\":409,\"status\":\"Duplicate\"}"),
                                    }
                            )
                    })
    })
    public ResponseEntity<EnquiryDTO> createEnquiry(
            @RequestBody EnquiryCreateRequest request
    ){
        return new ResponseEntity<>(
                enquiryService.createEnquiry(request),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Delete a enquiry by its id",
            description = "This endpoint is specifically for administrators to delete a enquiry by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204", description = "Delete successfully"),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The enquiry with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> deleteEnquiry(
            @PathVariable Long id
    ){
        enquiryService.deleteEnquiry(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Retrieve a enquiry by its id",
            description = "This endpoint is specifically for administrators to retrieve a enquiry by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnquiryDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"name\":\"Vo Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"comment\":\"This website has a beautiful and flexible appearance\",\"createdAt\":\"2024-01-12T00:53:55.1621267\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The enquiry with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<EnquiryDTO> getEnquiry(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                enquiryService.getEnquiry(id)
        );
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Enquiry pagination",
            description = "This endpoint is specifically for administrators to retrieve enquiry list by page and limit"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EnquiryDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"name\":\"Vo Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"comment\":\"This website has a beautiful and flexible appearance\",\"createdAt\":\"2024-01-12T00:53:55.1621267\"}]")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The enquiry with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
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
