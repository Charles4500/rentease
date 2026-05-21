package com.server.rentease.properties.controller;

import com.server.rentease.common.dto.ApiResponse;
import com.server.rentease.properties.dto.PropertyDTO;
import com.server.rentease.properties.exceptions.ResourceNotFoundException;
import com.server.rentease.properties.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private  final PropertyService propertyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<PropertyDTO> createProperty(@Valid @RequestBody PropertyDTO propertyDTO){
        PropertyDTO created = propertyService.createProperty(propertyDTO);
        return  ApiResponse.success(created,"Property created successfully");
    }

    @GetMapping("/{uuid}")
    ApiResponse<PropertyDTO> getProperty(@PathVariable UUID uuid) {
        try{
            PropertyDTO property = propertyService.getProperty(uuid);
            return ApiResponse.success(property, "Property retrieved successfully");
        }catch (ResourceNotFoundException ex){
            return ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/my-properties")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<List<PropertyDTO>> getMyProperties() {
        List<PropertyDTO> properties = propertyService.getPropertiesByLandlord();
        return ApiResponse.success(properties, "Your properties retrieved successfully");
    }

    @PatchMapping("/{uuid}")
    ApiResponse<PropertyDTO> updateProperty(
            @PathVariable UUID uuid,
            @Valid @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO updated = propertyService.updateProperty(uuid, propertyDTO);
        return ApiResponse.success(updated, "Property updated successfully");
    }

    @DeleteMapping("/{uuid}")
    ApiResponse<Void> deleteProperty(@PathVariable UUID uuid) {
        try {
            propertyService.deleteProperty(uuid);
            return ApiResponse.success(null, "Property deleted successfully");
        } catch (ResourceNotFoundException ex) {
            return ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

}
