package com.helper.coin.service.Coin;

import com.helper.coin.controller.dto.coin.CoinResponseDto;
import com.helper.coin.domain.coin.Coin;
import com.helper.coin.domain.coin.CoinRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinServiceTest {

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    CoinService coinService;

    /*
    @Test
    public void updateTest() throws Exception {
        coinRepository.save(Coin.builder().name("BTC").currency("KRW").exchange("upbit").build());
        List<Coin> coins = coinRepository.findAll();
        assertThat(coins.get(0).getBeforePrice5()).isNull();
        coinService.update();
        coins = coinRepository.findAll();
        System.out.println(coins.get(0).getNowPrice5().toString());
        assertThat(coins.get(0).getBeforePrice5()).isNotNull();
        assertThat(coins.get(0).getModifiedDate()).isBefore(LocalDateTime.now());
    }

    @Test
    public void insertAllTest() throws Exception {
        coinService.insertAll();
        List<Coin> coins = coinRepository.findAll();
        System.out.println(coins.size());
        assertThat(coins.size()).isGreaterThan(100);
    }

     */

    @Test
    public void insertAllTest() throws Exception {
        coinService.insertAll();
        List<Coin> coins = coinRepository.findAll();
        System.out.println(coins.size());
        assertThat(coins.size()).isGreaterThan(100);
        assertThat(coins.get(0).getCreatedDate()).isBefore(LocalDateTime.now());
    }
}
