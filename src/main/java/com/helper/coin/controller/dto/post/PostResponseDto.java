package com.helper.coin.controller.dto.post;

import com.helper.coin.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostResponseDto {
    private String userId;
    private String userName;
    private String title;
    private String content;
    private String coinName;

    public PostResponseDto(Post post, String userName){
        this.userId = post.getUserId();
        this.userName = userName;
        this.title = post.getTitle();
        this.content = post.getContent();
        this.coinName = post.getCoinName();
    }
}
