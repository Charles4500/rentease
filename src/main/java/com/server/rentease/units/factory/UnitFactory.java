package com.server.rentease.units.factory;

import com.server.rentease.units.dto.UnitDTO;
import com.server.rentease.units.entity.Unit;

import java.util.UUID;

public class UnitFactory {

    public static UnitDTO toDTO(Unit unit){

        return new UnitDTO(
                unit.getUuid(),
                unit.getRoomNo(),
                unit.getRoomType(),
                unit.getRentAmount(),
                unit.getOccupied(),
                unit.getProperty() != null ? unit.getProperty().getUuid() : null
        );
    }

    public static  Unit toEntity(UnitDTO unitDTO){
        Unit unit =new Unit();
        unit.setUuid(unitDTO.uuid() != null ? unitDTO.uuid() : UUID.randomUUID());
        unit.setRoomNo(unitDTO.roomNo());
        unit.setRoomType(unitDTO.roomType());
        unit.setRentAmount(unitDTO.rentAmount());
        unit.setOccupied(unitDTO.occupied() != null ? unitDTO.occupied() : Boolean.FALSE);

        return  unit;
    }
}
