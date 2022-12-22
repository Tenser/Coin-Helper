package com.helper.coin.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIsOkResponseDto {
    private String message;

    public UserIsOkResponseDto(String message){
        this.message = message;
    }
}
