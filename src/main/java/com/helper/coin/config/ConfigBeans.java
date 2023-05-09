package com.helper.coin.config;

import com.helper.coin.api.ExchangeApi;
import com.helper.coin.api.binance.BinanceApi;
import com.helper.coin.api.upbit.UpbitApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class ConfigBeans {
    @Bean(name = "upbitApi")
    public ExchangeApi upbitApi(){
        return new UpbitApi("asd", "asd", "https://api.upbit.com");
    }

    @Bean(name = "binanceApi")
    public ExchangeApi binanceApi(){
        return new BinanceApi("asd", "asd", "https://api1.binance.com");
    }

    @Bean(name = "units")
    public int[] units() {
        int[] units = {5, 15, 30, 60, 120, 240};
        return units;
    }

    @Bean(name = "coinInfos")
    public List<Map<String, Object>>[] coinInfos() { return new ArrayList[800]; }

}
