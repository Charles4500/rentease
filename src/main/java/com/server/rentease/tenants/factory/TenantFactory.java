package com.server.rentease.tenants.factory;

import com.server.rentease.tenants.dto.TenantDTO;
import com.server.rentease.tenants.entity.Tenant;

import java.util.UUID;

public class TenantFactory {

    public static TenantDTO toDTO(Tenant tenant) {
        return new TenantDTO(
                tenant.getUuid(),
                tenant.getName(),
                tenant.getEmail(),
                tenant.getPhone(),
                tenant.getPreferredBillingChannel(),
                tenant.getProperty() != null ? tenant.getProperty().getUuid() : null,
                tenant.getUnit() != null ? tenant.getUnit().getUuid() : null
        );
    }

    public static Tenant toEntity(TenantDTO tenantDTO) {
        Tenant tenant = new Tenant();
        tenant.setUuid(tenantDTO.uuid() != null ? tenantDTO.uuid() : UUID.randomUUID());
        tenant.setName(tenantDTO.name());
        tenant.setEmail(tenantDTO.email());
        tenant.setPhone(tenantDTO.phone());
        tenant.setPreferredBillingChannel(tenantDTO.preferredBillingChannel());
        return tenant;
    }
}