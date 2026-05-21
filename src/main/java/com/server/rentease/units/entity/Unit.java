package com.server.rentease.units.entity;

import com.server.rentease.common.entity.BaseEntity;
import com.server.rentease.properties.entity.Property;
import com.server.rentease.tenants.entity.Tenant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "units",
        uniqueConstraints = @UniqueConstraint(columnNames = {"property_id", "room_no"}))
public class Unit extends BaseEntity {

    @NotNull
    @Size(max = 50)
    @Column(name = "room_no",nullable = false,length = 50)
    private String roomNo;

    @Column(name = "room_type",nullable = false)
    private String roomType;

    @NotNull
    @Column(name = "rent_amount", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal rentAmount;

    @Column(name = "occupied")
    private Boolean  occupied = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @OneToOne(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Tenant tenant;
}
