package com.helper.coin.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {
    private String id;
    private String password;

    @Builder
    public UserLoginRequestDto(String id, String password){
        this.id = id;
        this.password = password;
    }
}
