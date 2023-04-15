package com.helper.coin.controller.dto.coin;

import com.helper.coin.domain.coin.Coin;
import com.helper.coin.domain.coin.CoinInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class CoinRankingResponseDto {

    private String name;

    private String currency;

    private Double nowPrice;

    private Double beforePrice;

    private Double nowVolume;

    private Double beforeVolume;

    private Double nowAmount;

    private Double beforeAmount;

    /*
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
     */

    public CoinRankingResponseDto(Map<String, Object> coinInfo){
        this.name = (String) coinInfo.get("coinName");
        this.currency = (String) coinInfo.get("currency");
        this.nowPrice = (Double) coinInfo.get("nowPrice");
        this.beforePrice = (Double) coinInfo.get("beforePrice");
        this.nowVolume = (Double) coinInfo.get("nowVolume");
        this.beforeVolume = (Double) coinInfo.get("beforeVolume");
        this.nowAmount = (Double) coinInfo.get("nowAmount");
        this.beforeAmount = (Double) coinInfo.get("beforeAmount");
    }

}
