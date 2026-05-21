package com.server.rentease.tenants.controller;

import com.server.rentease.common.dto.ApiResponse;
import com.server.rentease.tenants.dto.TenantDTO;
import com.server.rentease.tenants.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tenants")
@RequiredArgsConstructor
@Tag(name = "Tenants", description = "Manage tenant assignment and tenant records")
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/properties/{propertyUuid}/units/{unitUuid}")
    @ResponseStatus(HttpStatus.CREATED)
        @Operation(
            summary = "Create and assign a tenant",
            description = "Creates a tenant, links the tenant to a property and a unit, and marks the unit as occupied.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                          "name": "Jane Wanjiku",
                          "email": "jane@example.com",
                          "phone": "+254700123456",
                          "preferredBillingChannel": "WHATSAPP"
                        }
                        """)
                )
            )
        )
    ApiResponse<TenantDTO> createTenant(
            @Parameter(description = "Property UUID") @PathVariable UUID propertyUuid,
            @Parameter(description = "Unit UUID") @PathVariable UUID unitUuid,
            @Valid @RequestBody TenantDTO tenantDTO) {
        TenantDTO created = tenantService.createTenant(propertyUuid, unitUuid, tenantDTO);
        return ApiResponse.success(created, "Tenant created successfully");
    }

    @GetMapping("/{uuid}")
        @Operation(summary = "Get a tenant", description = "Returns a tenant by UUID.")
        ApiResponse<TenantDTO> getTenant(@Parameter(description = "Tenant UUID") @PathVariable UUID uuid) {
        TenantDTO tenant = tenantService.getTenant(uuid);
        return ApiResponse.success(tenant, "Tenant retrieved successfully");
    }

    @GetMapping("/properties/{propertyUuid}")
        @Operation(summary = "List tenants by property", description = "Returns all tenants assigned to a property.")
        ApiResponse<List<TenantDTO>> getTenantsByProperty(@Parameter(description = "Property UUID") @PathVariable UUID propertyUuid) {
        List<TenantDTO> tenants = tenantService.getTenantsByProperty(propertyUuid);
        return ApiResponse.success(tenants, "Tenants retrieved successfully");
    }

    @PatchMapping("/{uuid}")
        @Operation(
            summary = "Update a tenant",
            description = "Updates tenant contact details or billing preference.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                          "phone": "+254711000111",
                          "preferredBillingChannel": "EMAIL"
                        }
                        """)
                )
            )
        )
        ApiResponse<TenantDTO> updateTenant(@Parameter(description = "Tenant UUID") @PathVariable UUID uuid, @RequestBody TenantDTO tenantDTO) {
        TenantDTO updated = tenantService.updateTenant(uuid, tenantDTO);
        return ApiResponse.success(updated, "Tenant updated successfully");
    }

    @DeleteMapping("/{uuid}")
        @Operation(summary = "Delete a tenant", description = "Removes a tenant record and frees the assigned unit.")
        ApiResponse<Void> deleteTenant(@Parameter(description = "Tenant UUID") @PathVariable UUID uuid) {
        tenantService.deleteTenant(uuid);
        return ApiResponse.success(null, "Tenant deleted successfully");
    }
}