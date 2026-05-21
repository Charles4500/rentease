package com.server.rentease.properties.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonIgnoreProperties(value = {"created_date", "modified_date", "landlord_id"}, allowGetters = true)
public record PropertyDTO(

        @JsonProperty("uuid") UUID uuid,

        @NotNull(message = "Property name is required")
        @NotBlank(message = "Property name is required")
        String propertyName,

        @JsonProperty(value = "no_of_rooms",access = JsonProperty.Access.READ_ONLY)
        Integer noOfRooms,

        @NotNull(message = "Location  is required")
        @NotBlank(message = "Location  is required")
        @JsonProperty("location")
        String location,

        @JsonProperty(value = "landlord_id", access = JsonProperty.Access.READ_ONLY)
        UUID landlordId

) {
}
