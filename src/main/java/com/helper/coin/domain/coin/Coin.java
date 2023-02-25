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


    @Builder
    public Coin(String name, String currency, String exchange){
        this.name = name;
        this.currency = currency;
        this.exchange = exchange;
    }

    /*
    public void update(Double nowPrice5, Double beforePrice5, Double nowVolume5, Double beforeVolume5, Double nowPrice60, Double beforePrice60, Double nowVolume60, Double beforeVolume60, Double nowAmount5, Double beforeAmount5, Double nowAmount60, Double beforeAmount60){
        this.nowPrice5 = nowPrice5;
        this.beforePrice5 = beforePrice5;
        this.nowVolume5 = nowVolume5;
        this.beforeVolume5 = beforeVolume5;
        this.nowPrice60 = nowPrice60;
        this.beforePrice60 = beforePrice60;
        this.nowVolume60 = nowVolume60;
        this.beforeVolume60 = beforeVolume60;
        this.nowAmount5 = nowAmount5;
        this.beforeAmount5 = beforeAmount5;
        this.nowAmount60 = nowAmount60;
        this.beforeAmount60 = beforeAmount60;
    }
    */
}
