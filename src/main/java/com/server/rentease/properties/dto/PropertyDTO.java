package com.server.rentease.properties.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonIgnoreProperties(value = {"created_date", "modified_date", "landlord_id"}, allowGetters = true)
public record PropertyDTO(

        @Schema(description = "Property UUID", example = "5dca7a8d-0fc4-4721-b775-b32db0af6ae1", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty("uuid") UUID uuid,

        @NotNull(message = "Property name is required")
        @NotBlank(message = "Property name is required")
        @Schema(description = "Display name of the property", example = "Sunrise Apartments")
        String propertyName,

        @Schema(description = "Number of rooms in the property", example = "24", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty(value = "no_of_rooms",access = JsonProperty.Access.READ_ONLY)
        Integer noOfRooms,

        @NotNull(message = "Location  is required")
        @NotBlank(message = "Location  is required")
        @Schema(description = "Physical location of the property", example = "Kilimani, Nairobi")
        @JsonProperty("location")
        String location,

        @Schema(description = "Landlord UUID", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty(value = "landlord_id", access = JsonProperty.Access.READ_ONLY)
        UUID landlordId

) {
}
