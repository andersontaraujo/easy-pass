package br.com.devaware.easypass.passwords;

import java.util.Arrays;

public enum PasswordType {
    SOCIAL_NETWORK, SERVICE, FINANCIAL;

    public static PasswordType get(String type) {
        return Arrays.stream(PasswordType.values())
                .filter(t -> t.name().equals(type))
                .findFirst()
                .orElse(null);
    }
}
