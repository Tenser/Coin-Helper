package com.helper.coin.controller;

import com.helper.coin.controller.dto.UserSaveRequestDto;
import com.helper.coin.controller.dto.UserUpdatePasswordRequestDto;
import com.helper.coin.controller.dto.like.LikeSaveRequestDto;
import com.helper.coin.domain.coin.CoinRepository;
import com.helper.coin.domain.like.Like;
import com.helper.coin.domain.like.LikeRepository;
import com.helper.coin.domain.post.PostRepository;
import com.helper.coin.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikeControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private LikeRepository likeRepository;

    @After
    public void tearDown() throws Exception{
        userRepository.deleteAll();
        coinRepository.deleteAll();
        likeRepository.deleteAll();
    }

    @Test
    public void insertAndDeleteTest(){
        UserSaveRequestDto requestDto0 = UserSaveRequestDto.builder().id("admin").name("admin").password("admin").build();
        String url0 = "http://localhost:" + port + "/user/save";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url0, requestDto0, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        url0 = "http://localhost:" + port + "/coin/insertAll";
        responseEntity = restTemplate.getForEntity(url0, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        LikeSaveRequestDto requestDto = LikeSaveRequestDto.builder().userId("admin").coinId(1L).build();
        String url = "http://localhost:" + port + "/like/insert";
        responseEntity = restTemplate.postForEntity(url, requestDto, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Like> likes = likeRepository.findAll();
        assertThat(likes.get(0).getUserId()).isEqualTo("admin");

        url = "http://localhost:" + port + "/like/delete";
        responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<LikeSaveRequestDto>(requestDto), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        likes = likeRepository.findAll();
        assertThat(likes.size()).isEqualTo(0);
    }
}
