package com.server.rentease.properties.repository;

import com.server.rentease.properties.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property,Long> {

    Optional<Property> findByUuid(UUID uuid);

    List<Property> findByLandlordId(UUID landlordId);
}
