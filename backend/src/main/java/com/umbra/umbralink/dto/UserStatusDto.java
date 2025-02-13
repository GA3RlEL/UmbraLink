package com.umbra.umbralink.dto;

import com.umbra.umbralink.enums.UserStatus;
import lombok.Data;

@Data
public class UserStatusDto {
    private Long id;
    private UserStatus status;
}
