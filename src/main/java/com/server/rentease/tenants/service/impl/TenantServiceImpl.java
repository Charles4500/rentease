package com.server.rentease.tenants.service.impl;

import com.server.rentease.authentication.AuthContext;
import com.server.rentease.common.exceptions.UnauthorizedException;
import com.server.rentease.properties.entity.Property;
import com.server.rentease.properties.exceptions.ResourceNotFoundException;
import com.server.rentease.properties.repository.PropertyRepository;
import com.server.rentease.tenants.dto.TenantDTO;
import com.server.rentease.tenants.entity.Tenant;
import com.server.rentease.tenants.exceptions.TenantAssignmentException;
import com.server.rentease.tenants.factory.TenantFactory;
import com.server.rentease.tenants.repositories.TenantRepository;
import com.server.rentease.tenants.service.TenantService;
import com.server.rentease.units.entity.Unit;
import com.server.rentease.units.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantServiceImpl implements TenantService {

    private final AuthContext auth;
    private final TenantRepository tenantRepository;
    private final PropertyRepository propertyRepository;
    private final UnitRepository unitRepository;

    @Override
    public TenantDTO createTenant(UUID propertyUuid, UUID unitUuid, TenantDTO tenantDTO) {
        Property property = findPropertyOrThrow(propertyUuid);
        verifyOwnership(property);

        Unit unit = findUnitOrThrow(unitUuid);
        verifyUnitBelongsToProperty(unit, property);
        verifyUnitIsAvailable(unit);

        Tenant tenant = TenantFactory.toEntity(tenantDTO);
        tenant.setProperty(property);
        tenant.setUnit(unit);
        unit.setOccupied(Boolean.TRUE);

        tenant = tenantRepository.save(tenant);
        return TenantFactory.toDTO(tenant);
    }

    @Override
    @Transactional(readOnly = true)
    public TenantDTO getTenant(UUID uuid) {
        Tenant tenant = findTenantOrThrow(uuid);
        verifyOwnership(tenant.getProperty());
        return TenantFactory.toDTO(tenant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantDTO> getTenantsByProperty(UUID propertyUuid) {
        Property property = findPropertyOrThrow(propertyUuid);
        verifyOwnership(property);

        return tenantRepository.findByPropertyUuid(propertyUuid)
                .stream()
                .map(TenantFactory::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TenantDTO updateTenant(UUID uuid, TenantDTO tenantDTO) {
        Tenant tenant = findTenantOrThrow(uuid);
        verifyOwnership(tenant.getProperty());

        if (tenantDTO.name() != null) {
            tenant.setName(tenantDTO.name());
        }

        if (tenantDTO.email() != null) {
            tenant.setEmail(tenantDTO.email());
        }

        if (tenantDTO.phone() != null) {
            tenant.setPhone(tenantDTO.phone());
        }

        if (tenantDTO.preferredBillingChannel() != null) {
            tenant.setPreferredBillingChannel(tenantDTO.preferredBillingChannel());
        }

        tenant = tenantRepository.save(tenant);
        return TenantFactory.toDTO(tenant);
    }

    @Override
    public void deleteTenant(UUID uuid) {
        Tenant tenant = findTenantOrThrow(uuid);
        verifyOwnership(tenant.getProperty());
        tenant.getUnit().setOccupied(Boolean.FALSE);
        tenantRepository.delete(tenant);
    }

    private Tenant findTenantOrThrow(UUID uuid) {
        return tenantRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for UUID: " + uuid));
    }

    private Property findPropertyOrThrow(UUID propertyUuid) {
        return propertyRepository.findByUuid(propertyUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found for UUID: " + propertyUuid));
    }

    private Unit findUnitOrThrow(UUID unitUuid) {
        return unitRepository.findByUuid(unitUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found for UUID: " + unitUuid));
    }

    private void verifyOwnership(Property property) {
        if (!property.getLandlordId().equals(auth.getLandlordId())) {
            throw new UnauthorizedException("You don't have permission to modify tenants for this property");
        }
    }

    private void verifyUnitBelongsToProperty(Unit unit, Property property) {
        if (!unit.getProperty().getUuid().equals(property.getUuid())) {
            throw new TenantAssignmentException("Unit does not belong to the specified property");
        }
    }

    private void verifyUnitIsAvailable(Unit unit) {
        if (Boolean.TRUE.equals(unit.getOccupied()) || tenantRepository.findByUnitUuid(unit.getUuid()).isPresent()) {
            throw new TenantAssignmentException("Unit is already occupied");
        }
    }
}