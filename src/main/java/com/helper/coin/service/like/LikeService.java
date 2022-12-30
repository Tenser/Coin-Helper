package com.helper.coin.service.like;

import com.helper.coin.controller.dto.UserIsOkResponseDto;
import com.helper.coin.controller.dto.like.LikeSaveRequestDto;
import com.helper.coin.domain.like.Like;
import com.helper.coin.domain.like.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public UserIsOkResponseDto save(LikeSaveRequestDto requestDto){
        likeRepository.save(requestDto.toEntity());
        return new UserIsOkResponseDto("OK");
    }

    @Transactional
    public UserIsOkResponseDto delete(LikeSaveRequestDto requestDto){
        likeRepository.delete(likeRepository.findByUserIdAndCoinId(requestDto.getUserId(), requestDto.getCoinId()));
        return new UserIsOkResponseDto("OK");
    }
}
