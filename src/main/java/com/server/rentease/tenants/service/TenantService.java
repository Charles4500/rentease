package com.server.rentease.tenants.service;

import com.server.rentease.tenants.dto.TenantDTO;

import java.util.List;
import java.util.UUID;

public interface TenantService {

    TenantDTO createTenant(UUID propertyUuid, UUID unitUuid, TenantDTO tenantDTO);

    TenantDTO getTenant(UUID uuid);

    List<TenantDTO> getTenantsByProperty(UUID propertyUuid);

    TenantDTO updateTenant(UUID uuid, TenantDTO tenantDTO);

    void deleteTenant(UUID uuid);
}