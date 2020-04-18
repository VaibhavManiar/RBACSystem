package org.auth.util;

public interface PasswordEncoder {
    String decode(String encodedPassword);
    String encode(String password);
}
