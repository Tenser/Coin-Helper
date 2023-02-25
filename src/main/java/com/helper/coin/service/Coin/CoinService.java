package com.helper.coin.service.Coin;

import com.helper.coin.api.ExchangeApi;
import com.helper.coin.api.ExchangeRate;
import com.helper.coin.api.upbit.UpbitApi;
import com.helper.coin.controller.dto.coin.CoinLikeResponseDto;
import com.helper.coin.controller.dto.coin.CoinPremiumResponseDto;
import com.helper.coin.controller.dto.coin.CoinRankingResponseDto;
import com.helper.coin.controller.dto.coin.CoinResponseDto;
import com.helper.coin.domain.coin.Coin;
import com.helper.coin.domain.coin.CoinInfo;
import com.helper.coin.domain.coin.CoinInfoRepository;
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
    private final CoinInfoRepository coinInfoRepository;
    private final ExchangeApi upbitApi;
    private final ExchangeApi binanceApi;
    private Double exchangeRate;
    int[] units = {5, 30, 60, 120, 240};

    /*
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
    */

    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void update() throws Exception {
        List<Coin> upbitCoins = coinRepository.findByExchange("upbit");
        List<Coin> binanceCoins = coinRepository.findByExchange("binance");

        Map<String, Double> res;
        ExchangeRate.setExchangeRate();
        for(int i=0;i<Math.max(upbitCoins.size(), binanceCoins.size());i++) {
            if(upbitCoins.size() > i){
                Long coinId = upbitCoins.get(i).getId();
                for(int unit: units){
                    res = upbitApi.getMinuteCandle(unit, upbitCoins.get(i).getName(), upbitCoins.get(i).getCurrency());
                    coinInfoRepository.findByCoinIdAndUnit(coinId, unit).update(res.get("nowVolume"), res.get("beforeVolume"), res.get("nowPrice"), res.get("beforePrice"), res.get("nowAmount"), res.get("beforeAmount"));
                }
            }
            if(binanceCoins.size() > i){
                Long coinId = binanceCoins.get(i).getId();
                for(int unit: units){
                    res = binanceApi.getMinuteCandle(unit, binanceCoins.get(i).getName(), binanceCoins.get(i).getCurrency());
                    coinInfoRepository.findByCoinIdAndUnit(coinId, unit).update(res.get("nowVolume"), res.get("beforeVolume"), res.get("nowPrice"), res.get("beforePrice"), res.get("nowAmount"), res.get("beforeAmount"));
                }
            }
            Thread.sleep(200);
        }
        //System.out.println("finish!");
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
    public List<CoinRankingResponseDto> findAllOrderByVolumeUp(int unit, String currency, String exchange){
        List<CoinInfo> coinInfos = coinInfoRepository.findOrderByVolumeUp(currency, exchange, unit);
        List<CoinRankingResponseDto> responseDtos = new ArrayList<>();
        for (CoinInfo coinInfo: coinInfos){
            Coin coin = coinRepository.findById2(coinInfo.getCoinId());
            responseDtos.add(new CoinRankingResponseDto(coinInfo, coin));
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinRankingResponseDto> findAllOrderByPriceUp(int unit, String currency, String exchange){
        List<CoinInfo> coinInfos = coinInfoRepository.findOrderByPriceUp(currency, exchange, unit);
        List<CoinRankingResponseDto> responseDtos = new ArrayList<>();
        for (CoinInfo coinInfo: coinInfos){
            Coin coin = coinRepository.findById2(coinInfo.getCoinId());
            responseDtos.add(new CoinRankingResponseDto(coinInfo, coin));
        }
        return responseDtos;
    }

    @Transactional
    public CoinResponseDto insert(String name){
        return new CoinResponseDto(coinRepository.save(Coin.builder().name(name).currency("KRW").exchange("upbit").build()));
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
            coinInfoRepository.findByCoinIdAndUnit(coins.get(i).getId(), 5).getNowPrice();
            Double priceKorea = coinInfoRepository.findByCoinIdAndUnit(coins.get(i).getId(), 5).getNowPrice();
            Double priceAmerica = coinInfoRepository.findByCoinIdAndUnit(coins.get(i+n).getId(), 5).getNowPrice();
            Double premium = priceKorea / (priceAmerica * ExchangeRate.exchangeRate);
            premium = Math.round(premium * 10000) / 10000.0;
            responseDtos.add(new CoinPremiumResponseDto(coins.get(i).getName(), priceKorea, priceAmerica, premium));
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinResponseDto> insertAll() throws Exception {
        coinRepository.deleteAll();
        coinInfoRepository.deleteAll();
        List<CoinResponseDto> responseDtos = new ArrayList<>();
        List<Map<String, String>> res = upbitApi.getMarketAll();
        for(Map<String, String> market: res){
            Coin coin = coinRepository.save(Coin.builder().name(market.get("name")).currency(market.get("currency")).exchange("upbit").build());
            responseDtos.add(new CoinResponseDto(coin));
            for(int unit: units){
                coinInfoRepository.save(CoinInfo.builder().coinId(coin.getId()).unit(unit).build());
            }
        }
        res = binanceApi.getMarketAll().subList(0, 200);
        System.out.println(res.get(1).get("currency"));
        for(Map<String, String> market: res) {
            Coin coin = coinRepository.save(Coin.builder().name(market.get("name")).currency(market.get("currency")).exchange("binance").build());
            responseDtos.add(new CoinResponseDto(coin));
            for (int unit : units) {
                coinInfoRepository.save(CoinInfo.builder().coinId(coin.getId()).unit(unit).build());
            }
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinLikeResponseDto> showDetailByName(String name){
        List<Coin> coins = coinRepository.findAllByName(name);
        List<CoinLikeResponseDto> responseDtos = new ArrayList<>();
        for (Coin coin: coins){
            responseDtos.add(new CoinLikeResponseDto(coin));
        }
        return responseDtos;
    }

    @Transactional
    public List<CoinResponseDto> deleteAll(){
        coinRepository.deleteAll();
        coinInfoRepository.deleteAll();
        return new ArrayList<>();
    }

}
