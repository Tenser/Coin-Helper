package com.helper.coin.domain.coin;

import com.helper.coin.controller.dto.coin.CoinRankingResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface CoinInfoRepository extends JpaRepository<CoinInfo, Long> {

    @Query("select c from CoinInfo c where c.coinId=:coinId and c.unit=:unit")
    CoinInfo findByCoinIdAndUnit(Long coinId, int unit);

    @Query("select ci from CoinInfo ci left join Coin c on c.id=ci.coinId where c.currency=:currency and c.exchange=:exchange and ci.unit=:unit and ci.beforePrice > 0 order by ci.nowPrice/ci.beforePrice desc")
    ArrayList<CoinInfo> findOrderByPriceUp(String currency, String exchange, int unit);

    @Query("select ci from CoinInfo ci left join Coin c on c.id=ci.coinId where c.currency=:currency and c.exchange=:exchange and ci.unit=:unit and ci.beforeVolume > 0 order by ci.nowVolume/ci.beforeVolume desc")
    ArrayList<CoinInfo> findOrderByVolumeUp(String currency, String exchange, int unit);
}
