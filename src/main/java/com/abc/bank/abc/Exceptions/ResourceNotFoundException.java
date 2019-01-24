package com.abc.bank.abc.Exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private Integer resourceId;

    public ResourceNotFoundException(Integer resourceId, String message) {
        super(message + " with id " + resourceId);
        this.resourceId = resourceId;
    }
}
