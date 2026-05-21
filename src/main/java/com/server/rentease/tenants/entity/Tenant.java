package com.server.rentease.tenants.entity;

import com.server.rentease.billing.entity.BillingChannel;
import com.server.rentease.common.entity.BaseEntity;
import com.server.rentease.properties.entity.Property;
import com.server.rentease.units.entity.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tenants")
public class Tenant extends BaseEntity {

    @Size(max=100)
    @NotNull
    @Column(nullable = false)
    private  String name;

    @Column(nullable = true,unique = true)
    @Size(max=255, message = "Email must be at most 255 characters")
    private String email;

    @NotNull
    @Size(max=15, message = "Phone number must be at most 15 characters")
    @Column(nullable = false,unique = true)
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_billing_channel", nullable = false, length = 20)
    private BillingChannel preferredBillingChannel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false, unique = true)
    private Unit unit;
}
