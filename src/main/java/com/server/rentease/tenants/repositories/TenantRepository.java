package com.server.rentease.tenants.repositories;

import com.server.rentease.tenants.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByUuid(UUID uuid);

    List<Tenant> findByPropertyUuid(UUID propertyUuid);

    Optional<Tenant> findByUnitUuid(UUID unitUuid);
}