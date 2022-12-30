package com.helper.coin.domain.like;

import com.helper.coin.domain.coin.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select l from Like l where l.userId=:userId and l.coinId=:coinId")
    Like findByUserIdAndCoinId(String userId, Long coinId);
}
