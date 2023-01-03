package com.helper.coin.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTokenResponseDto {
    private String message;
    private String accessToken;
    private String refreshToken;

    public UserTokenResponseDto(String message, String accessToken, String refreshToken){
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
