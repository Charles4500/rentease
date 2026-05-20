package com.server.rentease.tenants.entity;

import com.server.rentease.common.entity.BaseEntity;
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

    @NotNull
    @Column(nullable = false,unique = true)
    @Size(max=255, message = "Email must be at most 255 characters")
    private String email;

    @Column(nullable = false)
    private String password;
}
