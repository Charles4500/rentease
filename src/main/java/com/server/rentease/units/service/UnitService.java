package com.server.rentease.units.service;

import com.server.rentease.units.dto.UnitDTO;

import java.util.List;
import java.util.UUID;

public interface UnitService {

	UnitDTO createUnit(UUID propertyUuid, UnitDTO unitDTO);

	UnitDTO getUnit(UUID uuid);

	List<UnitDTO> getUnitsByProperty(UUID propertyUuid);

	UnitDTO updateUnit(UUID uuid, UnitDTO unitDTO);

	void deleteUnit(UUID uuid);
}
