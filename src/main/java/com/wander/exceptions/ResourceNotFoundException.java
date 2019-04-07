package com.wander.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ResourceNotFoundException.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {
    
    /** The resource name. */
    private String resourceName;
    
    /** The field name. */
    private String fieldName;
    
    /** The field value. */
    private Object fieldValue;

    /**
     * Instantiates a new resource not found exception.
     *
     * @param resourceName the resource name
     * @param fieldName the field name
     * @param fieldValue the field value
     */
    public ResourceNotFoundException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}