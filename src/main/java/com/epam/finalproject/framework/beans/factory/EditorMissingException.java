package com.epam.finalproject.framework.beans.factory;

public class EditorMissingException extends RuntimeException {

    public EditorMissingException(Class<?> requiredType) {
        super("Editor for " + requiredType + " has not been implemented");
    }
}
