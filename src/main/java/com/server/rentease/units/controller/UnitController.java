package com.server.rentease.units.controller;

import com.server.rentease.common.dto.ApiResponse;
import com.server.rentease.units.dto.UnitDTO;
import com.server.rentease.units.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/units")
@RequiredArgsConstructor
@Tag(name = "Units", description = "Manage property units and room details")
public class UnitController {

	private final UnitService unitService;

	@PostMapping("/properties/{propertyUuid}")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
			summary = "Create a unit",
			description = "Creates a unit under a specific property.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					required = true,
					content = @Content(
							mediaType = "application/json",
							examples = @ExampleObject(value = """
								{
								  "roomNo": "A-12",
								  "roomType": "Bedsitter",
								  "rentAmount": 8500.00
								}
								""")
					)
			)
	)
	ApiResponse<UnitDTO> createUnit(@Parameter(description = "Property UUID") @PathVariable UUID propertyUuid, @Valid @RequestBody UnitDTO unitDTO) {
		UnitDTO created = unitService.createUnit(propertyUuid, unitDTO);
		return ApiResponse.success(created, "Unit created successfully");
	}

	@GetMapping("/{uuid}")
	@Operation(summary = "Get a unit", description = "Returns a single unit by UUID.")
	ApiResponse<UnitDTO> getUnit(@Parameter(description = "Unit UUID") @PathVariable UUID uuid) {
		UnitDTO unit = unitService.getUnit(uuid);
		return ApiResponse.success(unit, "Unit retrieved successfully");
	}

	@GetMapping("/properties/{propertyUuid}")
	@Operation(summary = "List units by property", description = "Returns all units belonging to a property.")
	ApiResponse<List<UnitDTO>> getUnitsByProperty(@Parameter(description = "Property UUID") @PathVariable UUID propertyUuid) {
		List<UnitDTO> units = unitService.getUnitsByProperty(propertyUuid);
		return ApiResponse.success(units, "Units retrieved successfully");
	}

	@PatchMapping("/{uuid}")
	@Operation(
			summary = "Update a unit",
			description = "Updates room details including rent amount.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					required = true,
					content = @Content(
							mediaType = "application/json",
							examples = @ExampleObject(value = """
								{
								  "roomType": "One Bedroom",
								  "rentAmount": 12000.00
								}
								""")
					)
			)
	)
	ApiResponse<UnitDTO> updateUnit(@Parameter(description = "Unit UUID") @PathVariable UUID uuid, @Valid @RequestBody UnitDTO unitDTO) {
		UnitDTO updated = unitService.updateUnit(uuid, unitDTO);
		return ApiResponse.success(updated, "Unit updated successfully");
	}

	@DeleteMapping("/{uuid}")
	@Operation(summary = "Delete a unit", description = "Deletes a unit by UUID.")
	ApiResponse<Void> deleteUnit(@Parameter(description = "Unit UUID") @PathVariable UUID uuid) {
		unitService.deleteUnit(uuid);
		return ApiResponse.success(null, "Unit deleted successfully");
	}
}
