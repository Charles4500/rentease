package com.server.rentease.properties.exceptions;

import com.server.rentease.common.exceptions.RecordNotFoundException;

public class ResourceNotFoundException extends RecordNotFoundException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}