package com.helper.coin.service.Coin;

import com.helper.coin.api.upbit.UpbitApi;
import com.helper.coin.domain.coin.Coin;
import com.helper.coin.domain.coin.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CoinService {

    private final CoinRepository coinRepository;
    private UpbitApi upbitApi;

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void update() throws Exception {
        upbitApi = new UpbitApi("asd", "asd", "https://api.upbit.com");
        List<Coin> coins = coinRepository.findAll();
        for(Coin coin: coins){
            Map<String, Double> res = upbitApi.getMinuteCandle(5, coin.getName(), coin.getCurrency(), 2);
            coin.update(res.get("nowPrice"), res.get("beforePrice"), res.get("nowVolume"), res.get("beforeVolume"));
        }
    }
}
