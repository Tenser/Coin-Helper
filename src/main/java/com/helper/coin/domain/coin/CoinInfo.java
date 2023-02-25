package com.helper.coin.domain.coin;

import com.helper.coin.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class CoinInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long coinId;

    private int unit;

    private Double nowVolume;

    private Double beforeVolume;

    private Double nowPrice;

    private Double beforePrice;

    private Double nowAmount;

    private Double beforeAmount;

    @Builder
    public CoinInfo(Long coinId, int unit){
        this.coinId = coinId;
        this.unit = unit;
    }

    public void update(Double nowVolume, Double beforeVolume, Double nowPrice, Double beforePrice, Double nowAmount, Double beforeAmount){
        this.nowPrice = nowPrice;
        this.beforePrice = beforePrice;
        this.nowVolume = nowVolume;
        this.beforeVolume = beforeVolume;
        this.nowAmount = nowAmount;
        this.beforeAmount = beforeAmount;
    }
}
