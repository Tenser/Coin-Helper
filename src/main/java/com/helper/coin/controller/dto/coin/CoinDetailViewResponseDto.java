package com.helper.coin.controller.dto.coin;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class CoinDetailViewResponseDto {
    String coinName;
    String currency;
    String exchange;
    List<CoinRankingResponseDto> dtos;

    public CoinDetailViewResponseDto(String coinName, String currency, String exchange, List<Map<String, Object>> coinInfos){
        this.coinName = coinName;
        this.currency = currency;
        this.exchange = exchange;
        dtos = new ArrayList<>();
        //System.out.println(coinInfos.toString());
        for (Map<String, Object> coinInfo: coinInfos){
            dtos.add(new CoinRankingResponseDto(coinInfo));
        }
    }

}
