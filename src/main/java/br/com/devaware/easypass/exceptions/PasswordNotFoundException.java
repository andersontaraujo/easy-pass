package br.com.devaware.easypass.exceptions;

public class PasswordNotFoundException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "Password with id [%s] was not found.";

    public PasswordNotFoundException(String id) {
        super(String.format(DEFAULT_ERROR_MESSAGE, id));
    }
}
