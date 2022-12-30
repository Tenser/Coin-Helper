package com.helper.coin.config;

import com.helper.coin.api.ExchangeApi;
import com.helper.coin.api.binance.BinanceApi;
import com.helper.coin.api.upbit.UpbitApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
}
