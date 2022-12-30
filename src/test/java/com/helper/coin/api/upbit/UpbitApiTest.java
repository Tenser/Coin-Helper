package com.helper.coin.api.upbit;

import com.helper.coin.api.binance.BinanceApi;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpbitApiTest {

    UpbitApi upbitApi;
    BinanceApi binanceApi;


    @Test
    public void getMinuteCandleTest() throws Exception {
        upbitApi = new UpbitApi("asd", "asd", "https://api.upbit.com");
        Map<String, Double> res = upbitApi.getMinuteCandle(5, "BTC", "KRW");
        System.out.println(res.toString());
        assertThat(res.toString()).contains("now");
    }

    @Test
    public void getMinuteCandleBinanceTest() throws Exception {
        binanceApi = new BinanceApi("asd", "asd", "https://api1.binance.com");
        Map<String, Double> res = binanceApi.getMinuteCandle(5, "BTC", "USDT");
        System.out.println(res.toString());
        assertThat(res.toString()).contains("1");
    }

    @Test
    public void getMarketAllTest() throws Exception {
        binanceApi = new BinanceApi("asd", "asd", "https://api1.binance.com");
        List<Map<String, String>> markets = binanceApi.getMarketAll();
        System.out.println(markets.get(0).toString());
    }
}
