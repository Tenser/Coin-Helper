package com.helper.coin.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.coinName=:coinName")
    public List<Post> findByCoinName(String coinName);
    
}
