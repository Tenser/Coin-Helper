package com.helper.coin.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateInformRequestDto {

    private String name;
    private String password;

    @Builder
    public UserUpdateInformRequestDto(String name, String password){
        this.name = name;
        this.password = password;
    }
}
