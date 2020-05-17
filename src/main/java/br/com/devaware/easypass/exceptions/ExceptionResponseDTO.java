package br.com.devaware.easypass.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponseDTO {
    private int statusCode;
    private String error;
    private String message;
}
