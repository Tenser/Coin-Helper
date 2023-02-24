package com.helper.coin.controller.dto.coin;

import com.helper.coin.domain.coin.Coin;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CoinResponseDto {

    private Long id;

    private String name;

    private String currency;

    private String exchange;

    private Double nowPrice;

    private Double beforePrice;

    private Double nowVolume;

    private Double beforeVolume;

    private Double nowAmount;

    private Double beforeAmount;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


    public CoinResponseDto(Coin coin, int unit){
        this.id = coin.getId();
        this.name = coin.getName();
        this.currency = coin.getCurrency();
        this.exchange = coin.getExchange();
        if(unit == 5){
            this.nowPrice = coin.getNowPrice5();
            this.beforePrice = coin.getBeforePrice5();
            this.nowVolume = coin.getNowVolume5();
            this.beforeVolume = coin.getBeforeVolume5();
            this.nowAmount = coin.getNowAmount5();
            this.beforeAmount = coin.getBeforeAmount5();
        }else if(unit == 60){
            this.nowPrice = coin.getNowPrice60();
            this.beforePrice = coin.getBeforePrice60();
            this.nowVolume = coin.getNowVolume60();
            this.beforeVolume = coin.getBeforeVolume60();
            this.nowAmount = coin.getNowAmount60();
            this.beforeAmount = coin.getBeforeAmount60();
        }
        this.createdDate = coin.getCreatedDate();
        this.modifiedDate = coin.getModifiedDate();

    }
}
