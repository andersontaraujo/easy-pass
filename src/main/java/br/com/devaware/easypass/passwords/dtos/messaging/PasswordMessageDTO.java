package br.com.devaware.easypass.passwords.dtos.messaging;

import br.com.devaware.easypass.passwords.dtos.response.PasswordDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordMessageDTO {
    private PasswordDTO password;
    private Boolean isComplete;
    @Builder.Default
    private List<PasswordMessageErrorDTO> errors = new ArrayList<>();
}
