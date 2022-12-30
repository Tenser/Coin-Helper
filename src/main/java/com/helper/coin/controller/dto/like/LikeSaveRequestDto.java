package com.helper.coin.controller.dto.like;

import com.helper.coin.domain.like.Like;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LikeSaveRequestDto {
    String userId;
    Long coinId;

    @Builder
    public LikeSaveRequestDto(String userId, Long coinId){
        this.userId = userId;
        this.coinId = coinId;
    }

    public Like toEntity(){
        return Like.builder().userId(userId).coinId(coinId).build();
    }

}
