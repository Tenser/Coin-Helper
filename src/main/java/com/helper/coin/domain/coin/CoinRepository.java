package com.helper.coin.domain.coin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    @Query("select c from Coin c where c.name like %:name%")
    List<Coin> findByName(String name);

    @Query("select c from Coin c where c.id=:id")
    Coin findById2(Long id);

    @Query("select c from Coin c left join Like l on c.id = l.coinId where l.userId=:userId")
    List<Coin> findByUserId(String userId);

    @Query("select c from Coin c where c.name in (select c2.name from Coin c2 where c2.exchange in ('upbit', 'binance') group by c2.name having count(c2.name) = 2) order by c.exchange desc, c.name")
    List<Coin> findPremium();

    @Query("select c from Coin c where c.exchange = :exchange")
    List<Coin> findByExchange(String exchange);

    @Query("select c from Coin c where c.name = :name")
    List<Coin> findAllByName(String name);

    @Query("select c from Coin c where c.currency = :currency and c.exchange = :exchange")
    List<Coin> findByCurrencyAndExchange(String currency, String exchange);
}
