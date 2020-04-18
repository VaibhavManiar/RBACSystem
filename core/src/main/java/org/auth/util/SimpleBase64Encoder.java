package org.auth.util;

import java.util.Base64;
import java.util.Objects;

public class SimpleBase64Encoder implements PasswordEncoder {
    @Override
    public String decode(String encodedPassword) {
        if (Objects.isNull(encodedPassword) || encodedPassword.trim().isEmpty())
            throw new RuntimeException("Decoding is not possible for empty or null string.");
        return new String(Base64.getDecoder().decode(encodedPassword));
    }

    @Override
    public String encode(String password) {
        if (Objects.isNull(password) || password.trim().isEmpty())
            throw new RuntimeException("Encoding is not possible for empty or null string.");
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
