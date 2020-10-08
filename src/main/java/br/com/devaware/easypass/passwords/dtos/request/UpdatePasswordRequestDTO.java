package br.com.devaware.easypass.passwords.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDTO {
    @NotBlank
    private String value;
    @NotBlank
    private String type;
}
