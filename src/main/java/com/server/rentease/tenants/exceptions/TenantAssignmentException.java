package com.server.rentease.tenants.exceptions;

public class TenantAssignmentException extends RuntimeException {
    public TenantAssignmentException(String message) {
        super(message);
    }
}