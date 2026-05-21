package com.server.rentease.units.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@JsonIgnoreProperties(value = {"created_date", "modified_date", "property_id"}, allowGetters = true)

public record UnitDTO(
        @Schema(description = "Unit UUID", example = "2b1f7ec2-453d-457d-b2a3-0db3a8b8454d", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty("uuid")
        UUID uuid,

        @NotNull(message = "Room number is required")
        @NotBlank(message = "Room number is required")
        @Schema(description = "Room or unit number", example = "A-12")
        String roomNo,

        @NotNull(message = "Room type is required")
        @NotBlank(message = "Room type is required")
        @Schema(description = "Type of room or unit", example = "Bedsitter")
        String roomType,

        @NotNull(message = "Rent amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Rent amount must be greater than zero")
        @Schema(description = "Monthly rent amount for the unit", example = "8500.00")
        BigDecimal rentAmount,

        @Schema(description = "Whether the unit is currently occupied", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty(value = "occupied",access = JsonProperty.Access.READ_ONLY)
        Boolean occupied,

        @Schema(description = "Property UUID this unit belongs to", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID propertyId
) {

}
