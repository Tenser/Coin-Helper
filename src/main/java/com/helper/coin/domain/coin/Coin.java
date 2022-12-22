package com.helper.coin.domain.coin;

import com.helper.coin.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Coin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String exchange;

    private Double nowPrice;

    private Double beforePrice;

    private Double nowVolume;

    private Double beforeVolume;


    @Builder
    public Coin(String name, String currency, String exchange){
        this.name = name;
        this.currency = currency;
        this.exchange = exchange;
    }

    public void update(Double nowPrice, Double beforePrice, Double nowVolume, Double beforeVolume){
        this.nowPrice = nowPrice;
        this.beforePrice = beforePrice;
        this.nowVolume = nowVolume;
        this.beforeVolume = beforeVolume;
    }

}
