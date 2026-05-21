package com.server.rentease.properties.entity;

import com.server.rentease.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "properties")
public class Property extends BaseEntity {

    @NotNull
    @Size(max=100)
    @Column(name = "property_name",nullable = false)
    private  String propertyName;

    @Column(name="no_of_rooms")
    private Integer noOfRooms;

    @NotNull
    @Column(name="location",nullable = false)
    private String location;

    @Column(name="landlord_id",nullable = false)
    UUID landlordId;

}
