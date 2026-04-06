package com.evcar;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptSqlGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        for (int i = 1; i <= 100; i++) {
            String userId = String.format("user%04d", i);
            String rawPassword = String.format("testev%02d", i - 1);
            String encodedPassword = encoder.encode(rawPassword);

            System.out.println(
                    "UPDATE user SET password = '" + encodedPassword + "' WHERE user_id = '" + userId + "';"
            );
        }
    }
}