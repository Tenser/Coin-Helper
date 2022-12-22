package com.helper.coin.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdatePasswordRequestDto {
    private String password;

    @Builder
    public UserUpdatePasswordRequestDto(String password){
        this.password = password;
    }
}
