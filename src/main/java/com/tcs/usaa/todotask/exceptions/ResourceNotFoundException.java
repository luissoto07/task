package com.tcs.usaa.todotask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String resourceName;
    private String campName;
    private long campValue;

    public ResourceNotFoundException(String resourceName, String campName, long campValue) {
        super(String.format("%s not found with %s: '%s' ", resourceName, campName, campValue));
        this.resourceName = resourceName;
        this.campName = campName;
        this.campValue = campValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public long getCampValue() {
        return campValue;
    }

    public void setCampValue(long campValue) {
        this.campValue = campValue;
    }
}
