package com.helper.coin.controller.dto;

import com.helper.coin.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String id;
    private String name;
    private String apiKey;
    private String secretKey;
    private int isOn;
    private int level;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.apiKey = user.getApiKey();
        this.secretKey = user.getSecretKey();
        this.isOn = user.getIsOn();
        this.level = user.getLevel();
    }

}
