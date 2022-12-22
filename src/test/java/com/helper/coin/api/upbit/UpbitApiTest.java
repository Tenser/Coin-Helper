package com.helper.coin.api.upbit;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpbitApiTest {

    UpbitApi upbitApi;


    @Test
    public void getMinuteCandleTest() throws Exception {
        upbitApi = new UpbitApi("asd", "asd", "https://api.upbit.com");
        Map<String, Double> res = upbitApi.getMinuteCandle(5, "BTC", "KRW", 2);
        System.out.println(res.toString());
        assertThat(res.toString()).contains("now");
    }
}
