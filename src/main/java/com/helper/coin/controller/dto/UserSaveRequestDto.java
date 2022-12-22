package com.helper.coin.controller.dto;

import com.helper.coin.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String id;
    private String name;
    private String password;
    private String apiKey;
    private String secretKey;

    @Builder
    public UserSaveRequestDto(String id, String password, String name, String apiKey, String secretKey){
        this.id = id;
        this.name = name;
        this.password = password;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public User toEntity(){
        return User.builder().id(id).name(name).password(password).apiKey(apiKey).secretKey(secretKey).build();
    }
}
