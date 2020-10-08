package br.com.devaware.easypass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    private static final String ARGUMENT_NOT_VALID_MESSAGE_STRUCTURE = "%s.%s: [%s].";

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionResponseDTO handleResourceNotFoundException(Exception e) {
        return ExceptionResponseDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .details(Collections.singletonList(e.getMessage()))
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponseDTO handleNotValidArgument(MethodArgumentNotValidException ex){
        List<String> messages = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> String.format(ARGUMENT_NOT_VALID_MESSAGE_STRUCTURE, field.getObjectName(), field.getField(), field.getDefaultMessage()))
                .collect(Collectors.toList());
        return ExceptionResponseDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .details(messages)
                .build();
    }

}
