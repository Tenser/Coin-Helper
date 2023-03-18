package com.helper.coin.controller.dto.coin;

import com.helper.coin.domain.coin.Coin;
import com.helper.coin.domain.coin.CoinInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CoinRankingResponseDto {

    private String name;

    private String currency;

    private String exchange;

    private int unit;

    private Double nowPrice;

    private Double beforePrice;

    private Double nowVolume;

    private Double beforeVolume;

    private Double nowAmount;

    private Double beforeAmount;


    public CoinRankingResponseDto(CoinInfo coinInfo, Coin coin){
        this.name = coin.getName();
        this.currency = coin.getCurrency();
        this.exchange = coin.getExchange();
        this.unit = coinInfo.getUnit();
        this.nowPrice = coinInfo.getNowPrice();
        this.beforePrice = coinInfo.getBeforePrice();
        this.nowVolume = coinInfo.getNowVolume();
        this.beforeVolume = coinInfo.getBeforeVolume();
        this.nowAmount = coinInfo.getNowAmount();
        this.beforeAmount = coinInfo.getBeforeAmount();
    }
}
