package com.server.rentease.tenants.dto;

import com.server.rentease.billing.entity.BillingChannel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@JsonIgnoreProperties(value = {"propertyId", "unitId"}, allowGetters = true)
public record TenantDTO(
        @Schema(description = "Tenant UUID", example = "a9f2d2c4-cc72-4e9d-b165-87ff1ac89a80", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty("uuid")
        UUID uuid,

        @NotNull(message = "Tenant name is required")
        @NotBlank(message = "Tenant name is required")
        @Schema(description = "Tenant full name", example = "Jane Wanjiku")
        String name,

        @Email(message = "Tenant email should be valid")
        @Schema(description = "Tenant email address", example = "jane@example.com")
        String email,

        @NotNull(message = "Tenant phone is required")
        @NotBlank(message = "Tenant phone is required")
        @Size(max = 15, message = "Tenant phone number must be at most 15 characters")
        @Schema(description = "Tenant phone number", example = "+254700123456")
        String phone,

        @NotNull(message = "Preferred billing channel is required")
        @Schema(description = "Preferred billing channel for the tenant", example = "WHATSAPP")
        BillingChannel preferredBillingChannel,

        @Schema(description = "Property UUID this tenant belongs to", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID propertyId,

        @Schema(description = "Unit UUID assigned to the tenant", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID unitId
) {
}