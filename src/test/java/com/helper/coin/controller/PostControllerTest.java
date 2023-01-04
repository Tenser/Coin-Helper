package com.helper.coin.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.helper.coin.controller.dto.UserSaveRequestDto;
import com.helper.coin.controller.dto.post.PostResponseDto;
import com.helper.coin.controller.dto.post.PostSaveRequestDto;
import com.helper.coin.domain.coin.Coin;
import com.helper.coin.domain.coin.CoinRepository;
import com.helper.coin.domain.post.Post;
import com.helper.coin.domain.post.PostRepository;
import com.helper.coin.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {
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

    @After
    public void tearDown() throws Exception{
        postRepository.deleteAll();
    }
    /*
    @Test
    public void saveTest(){
        String id = "admin";
        String url = "http://localhost:" + port + "/user/save";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, UserSaveRequestDto.builder().id(id).name("admin").password("admin").build(), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        url = "http://localhost:" + port + "/post/save";
        responseEntity = restTemplate.postForEntity(url, PostSaveRequestDto.builder().userId(id).title("admin").content("admin").coinName("BTC").build(), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("OK");

        List<Post> posts = postRepository.findAll();
        assertThat(posts.get(0).getUserId()).isEqualTo("admin");
        assertThat(posts.get(0).getTitle()).isEqualTo("admin");
    }

    @Test
    public void findByCoinNameTest(){
        String url = "http://localhost:" + port + "/user/save";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, UserSaveRequestDto.builder().id("admin").name("admin").password("admin").build(), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        url = "http://localhost:" + port + "/post/save";
        responseEntity = restTemplate.postForEntity(url, PostSaveRequestDto.builder().userId("admin").title("admin").content("admin").coinName("BTC").build(), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("OK");
        System.out.println("test");
        Coin coin = coinRepository.save(Coin.builder().name("BTC").build());
        System.out.println(coin.getId());

        url = "http://localhost:" + port + "/post/findByCoinName/BTC";
        responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody().toString());
    }

     */
}
