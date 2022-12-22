package com.helper.coin.controller;

import com.helper.coin.controller.dto.UserIsOkResponseDto;
import com.helper.coin.controller.dto.post.PostResponseDto;
import com.helper.coin.controller.dto.post.PostSaveRequestDto;
import com.helper.coin.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/post/save")
    public UserIsOkResponseDto save(@RequestBody PostSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    @GetMapping("/post/findByCoinName/{coinName}")
    public List<PostResponseDto> findByCoinName(@PathVariable String coinName){
        return postService.findByCoinName(coinName);
    }

    @DeleteMapping("/post/delete/{id}")
    public UserIsOkResponseDto delete(@PathVariable Long id){
        return postService.delete(id);
    }

}
