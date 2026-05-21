package com.server.rentease.units.service.impl;

import com.server.rentease.authentication.AuthContext;
import com.server.rentease.common.exceptions.UnauthorizedException;
import com.server.rentease.properties.entity.Property;
import com.server.rentease.properties.exceptions.ResourceNotFoundException;
import com.server.rentease.properties.repository.PropertyRepository;
import com.server.rentease.units.dto.UnitDTO;
import com.server.rentease.units.entity.Unit;
import com.server.rentease.units.factory.UnitFactory;
import com.server.rentease.units.repository.UnitRepository;
import com.server.rentease.units.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitServiceImpl implements UnitService {

	private final AuthContext auth;
	private final UnitRepository unitRepository;
	private final PropertyRepository propertyRepository;

	@Override
	public UnitDTO createUnit(UUID propertyUuid, UnitDTO unitDTO) {
		Property property = findPropertyOrThrow(propertyUuid);
		verifyOwnership(property);

		Unit unit = UnitFactory.toEntity(unitDTO);
		unit.setProperty(property);
		unit.setOccupied(Boolean.FALSE);

		unit = unitRepository.save(unit);
		return UnitFactory.toDTO(unit);
	}

	@Override
	@Transactional(readOnly = true)
	public UnitDTO getUnit(UUID uuid) {
		Unit unit = findUnitOrThrow(uuid);
		verifyOwnership(unit.getProperty());
		return UnitFactory.toDTO(unit);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UnitDTO> getUnitsByProperty(UUID propertyUuid) {
		Property property = findPropertyOrThrow(propertyUuid);
		verifyOwnership(property);

		return unitRepository.findByPropertyUuid(propertyUuid)
				.stream()
				.map(UnitFactory::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public UnitDTO updateUnit(UUID uuid, UnitDTO unitDTO) {
		Unit unit = findUnitOrThrow(uuid);
		verifyOwnership(unit.getProperty());

		if (unitDTO.roomNo() != null) {
			unit.setRoomNo(unitDTO.roomNo());
		}

		if (unitDTO.roomType() != null) {
			unit.setRoomType(unitDTO.roomType());
		}

		if (unitDTO.rentAmount() != null) {
			unit.setRentAmount(unitDTO.rentAmount());
		}

		unit = unitRepository.save(unit);
		return UnitFactory.toDTO(unit);
	}

	@Override
	public void deleteUnit(UUID uuid) {
		Unit unit = findUnitOrThrow(uuid);
		verifyOwnership(unit.getProperty());
		unitRepository.delete(unit);
	}

	private Unit findUnitOrThrow(UUID uuid) {
		return unitRepository.findByUuid(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("Unit not found for UUID: " + uuid));
	}

	private Property findPropertyOrThrow(UUID propertyUuid) {
		return propertyRepository.findByUuid(propertyUuid)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found for UUID: " + propertyUuid));
	}

	private void verifyOwnership(Property property) {
		if (!property.getLandlordId().equals(auth.getLandlordId())) {
			throw new UnauthorizedException("You don't have permission to modify units for this property");
		}
	}

}
