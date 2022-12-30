package com.helper.coin.controller.dto.coin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CoinPremiumResponseDto {
    private String coinName;
    private Double priceKorea;
    private Double priceAmerica;
    private Double premium;

    @Builder
    public CoinPremiumResponseDto(String coinName, Double priceKorea, Double priceAmerica, Double premium){
        this.coinName = coinName;
        this.priceKorea = priceKorea;
        this.priceAmerica = priceAmerica;
        this.premium = premium;
    }
}
