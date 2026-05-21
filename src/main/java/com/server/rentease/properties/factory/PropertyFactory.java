package com.server.rentease.properties.factory;

import com.server.rentease.properties.dto.PropertyDTO;
import com.server.rentease.properties.entity.Property;

import java.util.UUID;

public class PropertyFactory {

    public  static PropertyDTO toDTO(Property property){

        return new PropertyDTO(
                property.getUuid(),
                property.getPropertyName(),
                property.getNoOfRooms(),
                property.getLocation(),
                property.getLandlordId()
        );
    }

    public  static  Property toEntity(PropertyDTO propertyDTO){

        Property property = new Property();
        property.setUuid(propertyDTO.uuid() != null ? propertyDTO.uuid() : UUID.randomUUID());
        property.setPropertyName(propertyDTO.propertyName());
        property.setLocation(propertyDTO.location());
        property.setLandlordId(propertyDTO.landlordId());

        return  property;
    }
}
