package com.server.rentease.units.repository;

import com.server.rentease.units.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnitRepository extends JpaRepository<Unit, Long> {

	Optional<Unit> findByUuid(UUID uuid);

	List<Unit> findByPropertyUuid(UUID propertyUuid);
}
