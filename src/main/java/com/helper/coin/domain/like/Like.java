package com.helper.coin.domain.like;

import com.helper.coin.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.ws.BindingType;

@Getter
@NoArgsConstructor
@Entity
@Table(name="like_list")
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String userId;

    @Column(nullable = false)
    Long coinId;

    @Builder
    public Like(String userId, Long coinId){
        this.userId = userId;
        this.coinId = coinId;
    }
}
