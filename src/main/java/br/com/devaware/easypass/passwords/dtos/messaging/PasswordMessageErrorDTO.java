package br.com.devaware.easypass.passwords.dtos.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordMessageErrorDTO {
    private int statusCode;
    private String error;
    private List<String> details;
}
