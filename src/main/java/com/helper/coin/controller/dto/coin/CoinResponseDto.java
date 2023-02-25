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


    public CoinResponseDto(Coin coin){
        this.id = coin.getId();
        this.name = coin.getName();
        this.currency = coin.getCurrency();
        this.exchange = coin.getExchange();
        this.createdDate = coin.getCreatedDate();
        this.modifiedDate = coin.getModifiedDate();

    }
}
