package com.helper.coin.controller;

import com.helper.coin.controller.dto.coin.CoinLikeResponseDto;
import com.helper.coin.controller.dto.coin.CoinPremiumResponseDto;
import com.helper.coin.controller.dto.coin.CoinResponseDto;
import com.helper.coin.domain.coin.CoinRepository;
import com.helper.coin.service.Coin.CoinService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/coin/volume/ranking/{unit}")
    public List<CoinResponseDto> findAllOrderByVolumeUp(@PathVariable int unit){
        return coinService.findAllOrderByVolumeUp(unit);
    }

    @GetMapping("/coin/price/ranking/{unit}")
    public List<CoinResponseDto> findAllOrderByPriceUp(@PathVariable int unit){
        return coinService.findAllOrderByPriceUp(unit);
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
}
