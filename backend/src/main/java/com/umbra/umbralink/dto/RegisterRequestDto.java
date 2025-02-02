package com.umbra.umbralink.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String username;
    private String password;
}
