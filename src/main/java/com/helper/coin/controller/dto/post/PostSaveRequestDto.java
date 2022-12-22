package com.helper.coin.controller.dto.post;

import com.helper.coin.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostSaveRequestDto {
    private String userId;
    private String title;
    private String content;
    private String coinName;

    @Builder
    public PostSaveRequestDto(String userId, String title, String content, String coinName){
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.coinName = coinName;
    }

    public Post toEntity(){
        return Post.builder().userId(userId).title(title).content(content).coinName(coinName).build();
    }
}
