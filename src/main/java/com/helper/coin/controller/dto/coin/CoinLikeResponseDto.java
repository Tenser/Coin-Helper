package com.helper.coin.controller.dto.coin;

import com.helper.coin.domain.coin.Coin;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CoinLikeResponseDto {
    private Long id;

    private String name;

    private String currency;

    private String exchange;

    private Double nowPrice5;

    private Double beforePrice5;

    private Double nowVolume5;

    private Double beforeVolume5;

    private Double nowPrice60;

    private Double beforePrice60;

    private Double nowVolume60;

    private Double beforeVolume60;

    public CoinLikeResponseDto(Coin coin){
        this.id = coin.getId();
        this.name = coin.getName();
        this.currency = coin.getCurrency();
        this.exchange = coin.getExchange();
        this.nowPrice5 = coin.getNowPrice5();
        this.beforePrice5 = coin.getBeforePrice5();
        this.nowVolume5 = coin.getNowVolume5();
        this.beforeVolume5 = coin.getBeforeVolume5();
        this.nowPrice60 = coin.getNowPrice60();
        this.beforePrice60 = coin.getBeforePrice60();
        this.nowVolume60 = coin.getNowVolume60();
        this.beforeVolume60 = coin.getBeforeVolume60();
    }
}
