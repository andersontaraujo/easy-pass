package br.com.devaware.easypass.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "Password with id [%s] was not found.";

    public ResourceNotFoundException(String id) {
        super(String.format(DEFAULT_ERROR_MESSAGE, id));
    }
}
