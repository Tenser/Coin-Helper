package com.helper.coin.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTokenResponseDto {
    private String message;
    private String token;

    public UserTokenResponseDto(String message, String token){
        this.message = message;
        this.token = token;
    }
}
