package com.helper.coin.domain.post;

import com.helper.coin.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String title;

    private String content;

    private String coinName;

    @Builder
    private Post(String userId, String title, String content, String coinName){
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.coinName = coinName;
    }
}
