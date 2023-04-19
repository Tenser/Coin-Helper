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

import javax.validation.constraints.Null;
import java.awt.image.AreaAveragingScaleFilter;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CoinService {

    private final CoinRepository coinRepository;
    //private final CoinInfoRepository coinInfoRepository;
    private final ExchangeApi upbitApi;
    private final ExchangeApi binanceApi;
    private Double exchangeRate;
    //int[] units = {5, 30, 60, 120, 240};
    public static int[] units = {5, 30, 60, 120, 240};
    private List<Map<String, Object>>[] coinInfos = new ArrayList[500];

    /*
    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void update() throws Exception {
        List<Coin> coins = coinRepository.findAll();
        Map<String, Double> res5;
        Map<String, Double> res60;
        ExchangeRate.setExchangeRate();
        for(Coin coin: coins){
            if (coin.getExchange().equals("upbit")){
                res5 = upbitApi.getMinuteCandle(5, coin.getName(), coin.getCurrency(), 2);
                res60 = upbitApi.getMinuteCandle(60, coin.getName(), coin.getCurrency(), 2);
                Thread.sleep(200);
                //System.out.println(res5.get("nowPrice"));
                coin.update(res5.get("nowPrice"), res5.get("beforePrice"), res5.get("nowVolume"), res5.get("beforeVolume"), res60.get("nowPrice"), res60.get("beforePrice"),
                        res60.get("nowVolume"), res60.get("beforeVolume"));
                System.out.println(coinRepository.findById2(coin.getId()).getModifiedDate());

            }
            else{
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
    /*
    @Scheduled(initialDelay = 1000, fixedDelay = 10000)
    @Transactional
    public void update(){
        try{
            List<Coin> upbitCoins = coinRepository.findByExchange("upbit");
            List<Coin> binanceCoins = coinRepository.findByExchange("binance");
            LocalDateTime modifiedDate;
            List<Map<String, Double>> res;
            ExchangeRate.setExchangeRate();
            for(int i=0;i<Math.max(upbitCoins.size(), binanceCoins.size());i++) {
                if(upbitCoins.size() > i){
                    Long coinId = upbitCoins.get(i).getId();
                    res = upbitApi.getMinuteCandle(upbitCoins.get(i).getName(), upbitCoins.get(i).getCurrency());
                    //System.out.println(res);
                    for(int j = 0;j < units.length;j++){
                        modifiedDate = coinInfoRepository.findByCoinIdAndUnit(coinId, units[j]).update(res.get(j).get("nowVolume"),
                                res.get(j).get("beforeVolume"), res.get(j).get("nowPrice"), res.get(j).get("beforePrice"),
                                res.get(j).get("nowAmount"), res.get(j).get("beforeAmount"));
                        modifiedDate = coinInfoRepository.findByCoinIdAndUnit(coinId, units[j]).getModifiedDate();
                        System.out.println(upbitCoins.get(i).getName() + upbitCoins.get(i).getExchange() + modifiedDate);
                    }
                }
                if(binanceCoins.size() > i){
                    Long coinId = binanceCoins.get(i).getId();
                    res = binanceApi.getMinuteCandle(binanceCoins.get(i).getName(), binanceCoins.get(i).getCurrency());
                    //System.out.println(res);
                    for(int j = 0;j < units.length;j++){
                        modifiedDate = coinInfoRepository.findByCoinIdAndUnit(coinId, units[j]).update(res.get(j).get("nowVolume"),
                                res.get(j).get("beforeVolume"), res.get(j).get("nowPrice"), res.get(j).get("beforePrice"),
                                res.get(j).get("nowAmount"), res.get(j).get("beforeAmount"));
                        modifiedDate = coinInfoRepository.findByCoinIdAndUnit(coinId, units[j]).getModifiedDate();
                        System.out.println(binanceCoins.get(i).getName() + binanceCoins.get(i).getExchange() + modifiedDate);
                    }
                }
                Thread.sleep(200);
            }
            //System.out.println("finish!");
        }catch (Exception E){
            System.out.println(E);
        }
    }
    */
    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    @Transactional
    public void update(){
        try{
            List<Coin> upbitCoins = coinRepository.findByExchange("upbit");
            List<Coin> binanceCoins = coinRepository.findByExchange("binance");
            List<Map<String, Object>> res;
            ExchangeRate.setExchangeRate();
            for(int i=0;i<Math.max(upbitCoins.size(), binanceCoins.size());i++) {
                if(upbitCoins.size() > i){
                    res = upbitApi.getMinuteCandle(upbitCoins.get(i).getName(), upbitCoins.get(i).getCurrency(), upbitCoins.get(i).getExchange());
                    //System.out.println(res);
                    coinInfos[i] = res;
                }
                if(binanceCoins.size() > i){
                    res = binanceApi.getMinuteCandle(binanceCoins.get(i).getName(), binanceCoins.get(i).getCurrency(), binanceCoins.get(i).getExchange());
                    //System.out.println(res);
                    coinInfos[200+i] = res;
                }
                Thread.sleep(150);
            }
            //System.out.println("finish!");
            //System.out.println(coinInfos[150]);
        }catch (Exception E){
            System.out.println(E);
        }
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
    /*
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
     */
    @Transactional
    public List<CoinRankingResponseDto> findAllOrderByVolumeUp(int unit, String currency, String exchange){
        List<CoinRankingResponseDto> responseDtos = new ArrayList<>();
        int i = 0;
        for (int j=0;j<units.length;j++) {
            if (units[j] == unit){
                i = j;
                break;
            }
        }
        for (List<Map<String, Object>> coinInfo: coinInfos){
            if (coinInfo != null && coinInfo.get(i).get("currency").equals(currency) && coinInfo.get(i).get("exchange")
                    .equals(exchange) && (Double)coinInfo.get(i).get("beforeAmount") > 0.0)
                responseDtos.add(new CoinRankingResponseDto(coinInfo.get(i)));
        }
        Collections.sort(responseDtos, (a, b) -> (int)((b.getNowAmount() / b.getBeforeAmount() - a.getNowAmount() / a.getBeforeAmount()) * 1000)!=0
                ?(int)((b.getNowAmount() / b.getBeforeAmount() - a.getNowAmount() / a.getBeforeAmount()) * 1000):-1);
        return responseDtos;
    }

    @Transactional
    public List<CoinRankingResponseDto> findAllOrderByPriceUp(int unit, String currency, String exchange){
        List<CoinRankingResponseDto> responseDtos = new ArrayList<>();
        int i = 0;
        for (int j=0;j<units.length;j++) {
            if (units[j] == unit){
                i = j;
                break;
            }
        }
        for (List<Map<String, Object>> coinInfo: coinInfos){
            if (coinInfo != null && coinInfo.get(i).get("currency").equals(currency) && coinInfo.get(i).get("exchange").equals(exchange))
                responseDtos.add(new CoinRankingResponseDto(coinInfo.get(i)));
        }
        Collections.sort(responseDtos, (a, b) -> (int)((b.getNowPrice() / b.getBeforePrice() - a.getNowPrice() / a.getBeforePrice()) * 100));
        return responseDtos;
    }

    @Transactional
    public Map<String, Object> findAllOrderByVolumeUpMobile(int unit, String currency, String exchange){
        Map<String, Object> res = new HashMap<>();
        List<CoinRankingResponseDto> responseDtos = new ArrayList<>();
        int i = 0;
        for (int j=0;j<units.length;j++) {
            if (units[j] == unit){
                i = j;
                break;
            }
        }
        for (List<Map<String, Object>> coinInfo: coinInfos){
            if (coinInfo != null && coinInfo.get(i).get("currency").equals(currency) && coinInfo.get(i).get("exchange")
                    .equals(exchange) && (Double)coinInfo.get(i).get("beforeAmount") > 0.0)
                responseDtos.add(new CoinRankingResponseDto(coinInfo.get(i)));
        }
        Collections.sort(responseDtos, (a, b) -> (int)((b.getNowAmount() / b.getBeforeAmount() - a.getNowAmount() / a.getBeforeAmount()) * 1000)!=0
                ?(int)((b.getNowAmount() / b.getBeforeAmount() - a.getNowAmount() / a.getBeforeAmount()) * 1000):-1);
        res.put("updateTime", coinInfos[0].get(0).get("modifiedDate"));
        res.put("coins", responseDtos);
        return res;
    }

    @Transactional
    public Map<String, Object> findAllOrderByPriceUpMobile(int unit, String currency, String exchange){
        Map<String, Object> res = new HashMap<>();
        List<CoinRankingResponseDto> responseDtos = new ArrayList<>();
        int i = 0;
        for (int j=0;j<units.length;j++) {
            if (units[j] == unit){
                i = j;
                break;
            }
        }
        for (List<Map<String, Object>> coinInfo: coinInfos){
            if (coinInfo != null && coinInfo.get(i).get("currency").equals(currency) && coinInfo.get(i).get("exchange").equals(exchange))
                responseDtos.add(new CoinRankingResponseDto(coinInfo.get(i)));
        }
        Collections.sort(responseDtos, (a, b) -> (int)((b.getNowPrice() / b.getBeforePrice() - a.getNowPrice() / a.getBeforePrice()) * 100));
        res.put("updateTime", coinInfos[0].get(0).get("modifiedDate"));
        res.put("coins", responseDtos);
        return res;
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
/*
    @Transactional
    public List<CoinPremiumResponseDto> findPremium(){

        List<Coin> coins = coinRepository.findPremium();
        List<CoinPremiumResponseDto> responseDtos = new ArrayList<>();
        int n = coins.size()/2;

        for(int i=0;i<n;i++){
            Double priceKorea = coinInfoRepository.findByCoinIdAndUnit(coins.get(i).getId(), 5).getNowPrice();
            Double priceAmerica = coinInfoRepository.findByCoinIdAndUnit(coins.get(i+n).getId(), 5).getNowPrice();
            Double premium = priceKorea / (priceAmerica * ExchangeRate.exchangeRate);
            premium = Math.round(premium * 10000) / 10000.0;
            //premium = (premium - 1) * 100;
            responseDtos.add(new CoinPremiumResponseDto(coins.get(i).getName(), priceKorea, priceAmerica, premium));
        }
        return responseDtos;
    }

 */
    @Transactional
    public List<CoinPremiumResponseDto> findPremium(){
    List<Coin> coins = coinRepository.findPremium();
    List<CoinPremiumResponseDto> responseDtos = new ArrayList<>();
    int n = coins.size()/2;
    Map<String, Double[]> counts = new HashMap<>();
    for(int i=0;i<n;i++)
        counts.put(coins.get(i).getName(), new Double[2]);
    for (List<Map<String, Object>> coinInfo: coinInfos){
        if (coinInfo != null && counts.containsKey(coinInfo.get(0).get("coinName"))){
          if (coinInfo.get(0).get("exchange").equals("upbit")){
              counts.get(coinInfo.get(0).get("coinName"))[0] = (Double) coinInfo.get(0).get("nowPrice");
          }else{
              counts.get(coinInfo.get(0).get("coinName"))[1] = (Double) coinInfo.get(0).get("nowPrice");
          }
        }
    }
    //System.out.println(counts.get("BTC")[0]);
    for(int i=0;i<n;i++){
        Double priceKorea = counts.get(coins.get(i).getName())[0];
        Double priceAmerica = counts.get(coins.get(i+n).getName())[1];
        Double premium = priceKorea / (priceAmerica * ExchangeRate.exchangeRate);
        premium = Math.round(premium * 10000) / 10000.0;
        //premium = (premium - 1) * 100;
        responseDtos.add(new CoinPremiumResponseDto(coins.get(i).getName(), priceKorea, priceAmerica, premium));
    }
    return responseDtos;
}

    @Transactional
    public Map<String, Object> findPremiumMobile(){
        Map<String, Object> res = new HashMap<>();
        List<Coin> coins = coinRepository.findPremium();
        List<CoinPremiumResponseDto> responseDtos = new ArrayList<>();
        int n = coins.size()/2;
        Map<String, Double[]> counts = new HashMap<>();
        for(int i=0;i<n;i++)
            counts.put(coins.get(i).getName(), new Double[2]);
        for (List<Map<String, Object>> coinInfo: coinInfos){
            if (coinInfo != null && counts.containsKey(coinInfo.get(0).get("coinName"))){
                if (coinInfo.get(0).get("exchange").equals("upbit")){
                    counts.get(coinInfo.get(0).get("coinName"))[0] = (Double) coinInfo.get(0).get("nowPrice");
                }else{
                    counts.get(coinInfo.get(0).get("coinName"))[1] = (Double) coinInfo.get(0).get("nowPrice");
                }
            }
        }
        //System.out.println(counts.get("BTC")[0]);
        for(int i=0;i<n;i++){
            Double priceKorea = counts.get(coins.get(i).getName())[0];
            Double priceAmerica = counts.get(coins.get(i+n).getName())[1];
            Double premium = priceKorea / (priceAmerica * ExchangeRate.exchangeRate);
            premium = Math.round(premium * 10000) / 10000.0;
            //premium = (premium - 1) * 100;
            responseDtos.add(new CoinPremiumResponseDto(coins.get(i).getName(), priceKorea, priceAmerica, premium));
        }
        res.put("updateTime", coinInfos[0].get(0).get("modifiedDate"));
        res.put("coins", responseDtos);
        return res;
    }

    @Transactional
    public List<CoinResponseDto> insertAll() throws Exception {
        coinRepository.deleteAll();
        List<CoinResponseDto> responseDtos = new ArrayList<>();
        List<Map<String, String>> res = upbitApi.getMarketAll();
        for(Map<String, String> market: res){
            Coin coin = coinRepository.save(Coin.builder().name(market.get("name")).currency(market.get("currency")).exchange("upbit").build());
            responseDtos.add(new CoinResponseDto(coin));
        }
        res = binanceApi.getMarketAll().subList(0, 200);
        System.out.println(res.get(1).get("currency"));
        for(Map<String, String> market: res) {
            Coin coin = coinRepository.save(Coin.builder().name(market.get("name")).currency(market.get("currency")).exchange("binance").build());
            responseDtos.add(new CoinResponseDto(coin));
        }
        return responseDtos;
    }
    /*
    @Transactional
    public Map<String, Object> showDetailByName(String name){
        List<Coin> coins = coinRepository.findAllByName(name);
        Map<String, Object> res = new HashMap<>();
        List<CoinRankingResponseDto> responseDtos = new ArrayList<>();
        for (Coin coin: coins){
            List<CoinInfo> coinInfos = coinInfoRepository.findByCoinId(coin.getId());
            if (res.get("updateTime") == null)
                res.put("updateTime", coinInfos.get(0).getModifiedDate());
            for (CoinInfo coinInfo: coinInfos){
                responseDtos.add(new CoinRankingResponseDto(coinInfo, coin));
            }
        }
        res.put("coins", responseDtos);
        return res;
    }
     */

    @Transactional
    public List<CoinResponseDto> deleteAll(){
        coinRepository.deleteAll();
        return new ArrayList<>();
    }

    @Transactional
    public List<CoinResponseDto> test(){
        List<Coin> coins = coinRepository.findPremium();
        List<CoinResponseDto> responseDtos = new ArrayList<>();
        for (Coin coin: coins){
            responseDtos.add(new CoinResponseDto(coin));
        }
        return responseDtos;
    }

}
