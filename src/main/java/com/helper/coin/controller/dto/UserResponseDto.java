package com.helper.coin.controller.dto;

import com.helper.coin.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    String id;
    String name;
    String apiKey;
    String secretKey;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.apiKey = user.getApiKey();
        this.secretKey = user.getSecretKey();
    }

}
