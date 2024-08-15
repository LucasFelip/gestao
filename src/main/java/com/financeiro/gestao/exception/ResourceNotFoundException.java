package com.financeiro.gestao.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final Object resourceId;
    private final HttpStatus status;

    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(String.format("%s not found with id : '%s'", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.status = HttpStatus.NOT_FOUND;
    }

    public ResourceNotFoundException(String resourceName, Object resourceId, Throwable cause) {
        super(String.format("%s not found with id : '%s'", resourceName, resourceId), cause);
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.status = HttpStatus.NOT_FOUND;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Object getResourceId() {
        return resourceId;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
