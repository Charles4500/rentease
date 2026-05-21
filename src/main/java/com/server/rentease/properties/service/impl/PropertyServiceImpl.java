package com.server.rentease.properties.service.impl;

import com.server.rentease.authentication.AuthContext;
import com.server.rentease.common.exceptions.UnauthorizedException;
import com.server.rentease.properties.dto.PropertyDTO;
import com.server.rentease.properties.entity.Property;
import com.server.rentease.properties.exceptions.ResourceNotFoundException;
import com.server.rentease.properties.factory.PropertyFactory;
import com.server.rentease.properties.repository.PropertyRepository;
import com.server.rentease.properties.service.PropertyService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl  implements PropertyService {

    private  final AuthContext auth;
    private  final PropertyRepository propertyRepository;

    @Override
    @Transactional
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property property  = PropertyFactory.toEntity(propertyDTO);
        property.setLandlordId(auth.getLandlordId());

        property  = propertyRepository.save(property);
        return  PropertyFactory.toDTO(property);
    }

    @Override
    public PropertyDTO getProperty(UUID uuid) {
        Property property = findPropertyOrThrow(uuid);
        verifyOwnership(property);
        return PropertyFactory.toDTO(property);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyDTO> getPropertiesByLandlord() {
        UUID landlordId = auth.getLandlordId();
        return propertyRepository.findByLandlordId(landlordId)
                .stream()
                .map(PropertyFactory::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PropertyDTO updateProperty(UUID uuid,PropertyDTO propertyDTO){

        Property property = findPropertyOrThrow(uuid);
        verifyOwnership(property);

        if(property.getPropertyName() != null){
            property.setPropertyName(propertyDTO.propertyName());
        }

        if (propertyDTO.location() != null){
            property.setLocation(propertyDTO.location());
        }

        property = propertyRepository.save(property);
        return  PropertyFactory.toDTO(property);

    }

    @Override
    @Transactional
    public void deleteProperty(UUID uuid) {
        Property property = findPropertyOrThrow(uuid);
        verifyOwnership(property);
        propertyRepository.delete(property);
    }

    private Property findPropertyOrThrow(UUID uuid){
        return  propertyRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Property not found for UUID: " + uuid));
    }

    private void verifyOwnership(Property property) {
        if (!property.getLandlordId().equals(auth.getLandlordId())) {
            throw new UnauthorizedException("You don't have permission to modify this property");
        }
    }
}
