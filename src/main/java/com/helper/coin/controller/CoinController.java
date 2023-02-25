package com.helper.coin.controller;

import com.helper.coin.controller.dto.coin.CoinLikeResponseDto;
import com.helper.coin.controller.dto.coin.CoinPremiumResponseDto;
import com.helper.coin.controller.dto.coin.CoinRankingResponseDto;
import com.helper.coin.controller.dto.coin.CoinResponseDto;
import com.helper.coin.domain.coin.CoinRepository;
import com.helper.coin.service.Coin.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CoinController {

    private final CoinService coinService;

    @GetMapping("/coin/findAll")
    public List<CoinLikeResponseDto> findAll(){
        return coinService.findAll();
    }

    @GetMapping("/coin/volume/ranking/{unit}/{currency}/{exchange}")
    public List<CoinRankingResponseDto> findAllOrderByVolumeUp(@PathVariable int unit, @PathVariable String currency, @PathVariable String exchange){
        return coinService.findAllOrderByVolumeUp(unit, currency, exchange);
    }

    @GetMapping("/coin/price/ranking/{unit}/{currency}/{exchange}")
    public List<CoinRankingResponseDto> findAllOrderByPriceUp(@PathVariable int unit, @PathVariable String currency, @PathVariable String exchange){
        return coinService.findAllOrderByPriceUp(unit, currency, exchange);
    }


    @GetMapping("/coin/insert/{name}")
    public CoinResponseDto insert(@PathVariable String name){
        return coinService.insert(name);
    }

    @GetMapping("/coin/insertAll")
    public List<CoinResponseDto> insertAll() throws Exception {
        return coinService.insertAll();
    }

    @GetMapping("coin/search/{query}")
    public List<CoinLikeResponseDto> search(@PathVariable String query){
        return coinService.findByName(query);
    }

    @GetMapping("/coin/findByLike/{userId}")
    public List<CoinLikeResponseDto> findByLike(@PathVariable String userId){
        return coinService.findByUserId(userId);
    }

    @GetMapping("/coin/premium")
    public List<CoinPremiumResponseDto> findPremium(){
        return coinService.findPremium();
    }

    @GetMapping("coin/detailView/{name}")
    public List<CoinLikeResponseDto> detailView(@PathVariable String name){
        return coinService.showDetailByName(name);
    }
}
