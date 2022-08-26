package com.epam.finalproject.framework.data.sql.mapping;

public class EntityMappingException extends RuntimeException{

    public EntityMappingException(String message) {
        super(message);
    }

    public EntityMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityMappingException(Throwable cause) {
        super(cause);
    }
}
