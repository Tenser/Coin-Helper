package com.helper.coin.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateInformRequestDto {

    private String name;
    private String apiKey;
    private String secretKey;

    @Builder
    public UserUpdateInformRequestDto(String name, String apiKey, String secretKey){
        this.name = name;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }
}
