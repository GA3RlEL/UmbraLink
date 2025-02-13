package com.umbra.umbralink.dto.auth;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String username;
    private String password;
}
