package com.server.rentease.properties.service;

import com.server.rentease.properties.dto.PropertyDTO;

import java.util.List;
import java.util.UUID;

public  interface PropertyService {

    PropertyDTO createProperty(PropertyDTO propertyDTO);

    PropertyDTO updateProperty(UUID uuid, PropertyDTO propertyDTO);

    PropertyDTO getProperty(UUID uuid);

    List<PropertyDTO> getPropertiesByLandlord();

    void deleteProperty(UUID uuid);

}