package com.helper.coin.controller;

import com.helper.coin.controller.dto.UserIsOkResponseDto;
import com.helper.coin.controller.dto.like.LikeSaveRequestDto;
import com.helper.coin.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like/insert")
    public UserIsOkResponseDto insert(@RequestBody LikeSaveRequestDto requestDto){
        return likeService.save(requestDto);
    }

    @DeleteMapping("/like/delete")
    public UserIsOkResponseDto delete(@RequestBody LikeSaveRequestDto requestDto){
        return likeService.delete(requestDto);
    }
}
