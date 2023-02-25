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
    }
}
