package br.com.devaware.easypass.passwords;

import br.com.devaware.easypass.passwords.dtos.request.PasswordPartialDTO;
import br.com.devaware.easypass.passwords.dtos.response.PasswordDTO;

import java.time.LocalDateTime;

public final class PasswordMock {

    public static final String ID = "AAAAAAAAA";
    private static final String VALUE = "123456";
    private static final String TYPE = "SOCIAL_NETWORK";
    private static final LocalDateTime CREATED_MODIFIED_DATE = LocalDateTime.of(2020, 10, 13, 10, 0, 0);

    public static Password mockPasswordPartialInputMapping() {
        return Password.builder()
                .value(VALUE)
                .type(PasswordType.SOCIAL_NETWORK)
                .build();
    }

    public static PasswordPartialDTO mockPasswordPartialInput() {
        return PasswordPartialDTO.builder()
                .type(TYPE)
                .value(VALUE)
                .build();
    }

    public static Password mockPassword() {
        return Password.builder()
                .id(ID)
                .value(VALUE)
                .type(PasswordType.SOCIAL_NETWORK)
                .createdDate(CREATED_MODIFIED_DATE)
                .modifiedDate(CREATED_MODIFIED_DATE)
                .build();
    }

    public static PasswordDTO mockPasswordOutput() {
        return PasswordDTO.builder()
                .id(ID)
                .value(VALUE)
                .type(TYPE)
                .createdDate(CREATED_MODIFIED_DATE)
                .build();
    }

}
