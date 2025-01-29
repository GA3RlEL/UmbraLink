package com.umbra.umbralink.dto;

import com.umbra.umbralink.model.enums.UserStatus;
import lombok.Data;

@Data
public class UserStatusDto {
    private Long id;
    private UserStatus status;
}
