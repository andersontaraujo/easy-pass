package br.com.devaware.easypass.passwords.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PasswordPartialDTO {
    @NotBlank
    private String value;
    @NotBlank
    private String type;
}
