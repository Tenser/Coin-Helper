package com.helper.coin.service.Coin;

import com.helper.coin.api.ExchangeApi;
import com.helper.coin.api.ExchangeRate;
import com.helper.coin.api.upbit.UpbitApi;
import com.helper.coin.controller.dto.coin.CoinLikeResponseDto;
import com.helper.coin.controller.dto.coin.CoinPremiumResponseDto;
import com.helper.coin.controller.dto.coin.CoinResponseDto;
import com.helper.coin.domain.coin.Coin;
import com.helper.coin.domain.coin.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CoinService {

    private final CoinRepository coinRepository;
    private final ExchangeApi upbitApi;
    private final ExchangeApi binanceApi;
    private Double exchangeRate;

    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void update() throws Exception {
        List<Coin> coins = coinRepository.findAll();
        Map<String, Double> res5;
        Map<String, Double> res60;
        ExchangeRate.setExchangeRate();
        for(Coin coin: coins){
            if (coin.getExchange().equals("upbit")){
                res5 = upbitApi.getMinuteCandle(5, coin.getName(), coin.getCurrency());
                res60 = upbitApi.getMinuteCandle(60, coin.getName(), coin.getCurrency());
                Thread.sleep(200);
                //System.out.println(res5.get("nowPrice"));
                coin.update(res5.get("nowPrice"), res5.get("beforePrice"), res5.get("nowVolume"), res5.get("beforeVolume"), res60.get("nowPrice"), res60.get("beforePrice"), res60.get("nowVolume"), res60.get("beforeVolume"));
            }else{
                res5 = binanceApi.getMinuteCandle(5, coin.getName(), coin.getCurrency());
                res60 = binanceApi.getMinuteCandle(60, coin.getName(), coin.getCurrency());
                Thread.sleep(100);
                //System.out.println(coin.getName() + coin.getExchange());
                coin.update(res5.get("nowPrice"), res5.get("beforePrice"), res5.get("nowVolume"), res5.get("beforeVolume"), res60.get("nowPrice"), res60.get("beforePrice"), res60.get("nowVolume"), res60.get("beforeVolume"));
            }
        }
        System.out.println("finish!");
    }

    @Transactional
    public List<CoinLikeResponseDto> findAll(){
        List<Coin> coins = coinRepository.findAll();
        List<CoinLikeResponseDto> responseDtos = new ArrayList<>();
        for(Coin coin: coins){
            responseDtos.add(new CoinLikeResponseDto(coin));
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinLikeResponseDto> findByName(String name){
        List<Coin> coins = coinRepository.findByName(name);
        List<CoinLikeResponseDto> responseDtos = new ArrayList<>();
        for(Coin coin: coins){
            responseDtos.add(new CoinLikeResponseDto(coin));
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinResponseDto> findAllOrderByVolumeUp(int unit){
        List<Coin> coins;
        if (unit == 5){
            coins = coinRepository.findAllOrderByVolumeUp5();
        }else if (unit == 60){
            coins = coinRepository.findAllOrderByVolumeUp60();
        }else{
            coins = coinRepository.findAllOrderByVolumeUp5();
        }
        List<CoinResponseDto> responseDtos = new ArrayList<>();
        for (Coin coin: coins){
            //System.out.println(coin.getNowPrice());
            responseDtos.add(new CoinResponseDto(coin, unit));
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinResponseDto> findAllOrderByPriceUp(int unit){
        List<Coin> coins;
        if (unit == 5){
            coins = coinRepository.findAllOrderByPriceUp5();
        }else if (unit == 60){
            coins = coinRepository.findAllOrderByPriceUp60();
        }else{
            coins = coinRepository.findAllOrderByPriceUp5();
        }
        List<CoinResponseDto> responseDtos = new ArrayList<>();
        for (Coin coin: coins){
            //System.out.println(coin.getNowPrice());
            responseDtos.add(new CoinResponseDto(coin, unit));
        }
        return responseDtos;
    }

    @Transactional
    public CoinResponseDto insert(String name){
        return new CoinResponseDto(coinRepository.save(Coin.builder().name(name).currency("KRW").exchange("upbit").build()), 5);
    }



    @Transactional
    public List<CoinLikeResponseDto> findByUserId(String userId){
        List<Coin> coins = coinRepository.findByUserId(userId);
        List<CoinLikeResponseDto> responseDtos = new ArrayList<>();
        for(Coin coin: coins){
            responseDtos.add(new CoinLikeResponseDto(coin));
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinPremiumResponseDto> findPremium(){
        List<Coin> coins = coinRepository.findPremium();
        List<CoinPremiumResponseDto> responseDtos = new ArrayList<>();
        int n = coins.size()/2;
        for(int i=0;i<n;i++){
            Double priceKorea = coins.get(i).getNowPrice5();
            Double priceAmerica = coins.get(i+n).getNowPrice5();
            Double premium = priceKorea / (priceAmerica * ExchangeRate.exchangeRate);
            responseDtos.add(new CoinPremiumResponseDto(coins.get(i).getName(), priceKorea, priceAmerica, premium));
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinResponseDto> insertAll() throws Exception {
        coinRepository.deleteAll();
        List<CoinResponseDto> responseDtos = new ArrayList<>();
        List<Map<String, String>> res = upbitApi.getMarketAll();
        for(Map<String, String> market: res){
            responseDtos.add(new CoinResponseDto(coinRepository.save(Coin.builder().name(market.get("name")).currency(market.get("currency")).exchange("upbit").build()), 5));
        }
        res = binanceApi.getMarketAll().subList(0, 200);
        System.out.println(res.get(1).get("currency"));
        for(Map<String, String> market: res){
            responseDtos.add(new CoinResponseDto(coinRepository.save(Coin.builder().name(market.get("name")).currency(market.get("currency")).exchange("binance").build()), 5));
        }
        return responseDtos;
    }

}
