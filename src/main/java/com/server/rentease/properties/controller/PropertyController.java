package com.server.rentease.properties.controller;

import com.server.rentease.common.dto.ApiResponse;
import com.server.rentease.properties.dto.PropertyDTO;
import com.server.rentease.properties.exceptions.ResourceNotFoundException;
import com.server.rentease.properties.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/properties")
@RequiredArgsConstructor
@Tag(name = "Properties", description = "Manage landlord properties")
public class PropertyController {

    private  final PropertyService propertyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a property",
            description = "Creates a new property owned by the authenticated landlord.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "propertyName": "Sunrise Apartments",
                                      "location": "Nairobi West"
                                    }
                                    """)
                    )
            )
    )
    ApiResponse<PropertyDTO> createProperty(@Valid @RequestBody PropertyDTO propertyDTO){
        PropertyDTO created = propertyService.createProperty(propertyDTO);
        return  ApiResponse.success(created,"Property created successfully");
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a property", description = "Returns a property by UUID if it belongs to the authenticated landlord.")
    ApiResponse<PropertyDTO> getProperty(@Parameter(description = "Property UUID") @PathVariable UUID uuid) {
        try{
            PropertyDTO property = propertyService.getProperty(uuid);
            return ApiResponse.success(property, "Property retrieved successfully");
        }catch (ResourceNotFoundException ex){
            return ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/my-properties")
    @ResponseStatus(HttpStatus.OK)
        @Operation(summary = "List my properties", description = "Returns all properties owned by the authenticated landlord.")
    ApiResponse<List<PropertyDTO>> getMyProperties() {
        List<PropertyDTO> properties = propertyService.getPropertiesByLandlord();
        return ApiResponse.success(properties, "Your properties retrieved successfully");
    }

    @PatchMapping("/{uuid}")
        @Operation(
            summary = "Update a property",
            description = "Updates a property owned by the authenticated landlord.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                          "propertyName": "Sunrise Apartments Annex",
                          "location": "South B"
                        }
                        """)
                )
            )
        )
    ApiResponse<PropertyDTO> updateProperty(
            @Parameter(description = "Property UUID") @PathVariable UUID uuid,
            @Valid @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO updated = propertyService.updateProperty(uuid, propertyDTO);
        return ApiResponse.success(updated, "Property updated successfully");
    }

    @DeleteMapping("/{uuid}")
        @Operation(summary = "Delete a property", description = "Deletes a property owned by the authenticated landlord.")
        ApiResponse<Void> deleteProperty(@Parameter(description = "Property UUID") @PathVariable UUID uuid) {
        try {
            propertyService.deleteProperty(uuid);
            return ApiResponse.success(null, "Property deleted successfully");
        } catch (ResourceNotFoundException ex) {
            return ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

}
