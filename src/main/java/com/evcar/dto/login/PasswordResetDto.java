package com.evcar.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetDto {

    private String loginId;
    private String name;
    private String email;
    private String newPassword;
    private String newPasswordConfirm;
}