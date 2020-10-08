package br.com.devaware.easypass.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExceptionResponseDTO {
    private int statusCode;
    private String error;
    private List<String> details;
}
