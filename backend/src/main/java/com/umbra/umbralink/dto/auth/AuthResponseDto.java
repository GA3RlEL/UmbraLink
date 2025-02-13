package com.umbra.umbralink.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDto {

  private String token;
  private String tokenType = "Bearer ";

  public AuthResponseDto(String token)
  {
    this.token = token;
  }

}
