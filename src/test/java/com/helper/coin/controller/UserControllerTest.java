package com.helper.coin.controller;

import com.helper.coin.controller.dto.UserLoginRequestDto;
import com.helper.coin.controller.dto.UserSaveRequestDto;
import com.helper.coin.controller.dto.UserUpdateInformRequestDto;
import com.helper.coin.controller.dto.UserUpdatePasswordRequestDto;
import com.helper.coin.domain.user.User;
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
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception{
        userRepository.deleteAll();
    }
    /*
    @Test
    public void saveTest() throws Exception{
        String id = "admin@@";
        String password = "admin";

        //UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).password(password).apiKey("key").secretKey("key").build();
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).name("admin").password(password).build();

        String url = "http://localhost:" + port + "/user/save";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getId()).isEqualTo(id);
        assertThat(all.get(0).getPassword()).isEqualTo(password);
    }

    @Test
    public void loginTest() throws Exception{
        String id = "admin";
        String password = "admin";

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).password(password).build();
        String url = "http://localhost:" + port + "/user/save";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserLoginRequestDto requestDto2 = UserLoginRequestDto.builder().id("admin").password("admin").build();
        url = "http://localhost:" + port + "/user/login";
        responseEntity = restTemplate.postForEntity(url, requestDto2, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("NO");
    }

    @Test
    public void idCheckTest() throws Exception{
        String id = "admin";
        String password = "admin";

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).name("admin").password(password).build();
        String url = "http://localhost:" + port + "/user/save";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        url = "http://localhost:" + port + "/user/idCheck/admin2";
        responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("OK");
    }

    @Test
    public void updatePasswordTest() throws Exception{
        String id = "admin";
        String password = "admin";

        //UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).password(password).apiKey("key").secretKey("key").build();
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).password(password).build();

        String url = "http://localhost:" + port + "/user/save";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        url = "http://localhost:" + port + "/user/updatePassword/admin";
        UserUpdatePasswordRequestDto requestDto2 = UserUpdatePasswordRequestDto.builder().password("admin2").build();
        HttpEntity<UserUpdatePasswordRequestDto> requestEntity = new HttpEntity<UserUpdatePasswordRequestDto>(requestDto2);
        responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getId()).isEqualTo(id);
        assertThat(all.get(0).getPassword()).isEqualTo("admin2");
    }

    @Test
    public void updateInformTest() throws Exception{
        String id = "admin";
        String apikey = "admin";
        String secretKey = "admin";

        //UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).password(password).apiKey("key").secretKey("key").build();
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).password("admin").build();

        String url = "http://localhost:" + port + "/user/save";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        url = "http://localhost:" + port + "/user/updateInform/admin";
        UserUpdateInformRequestDto requestDto2 = UserUpdateInformRequestDto.builder().apiKey(apikey).secretKey(secretKey).build();
        HttpEntity<UserUpdateInformRequestDto> requestEntity = new HttpEntity<UserUpdateInformRequestDto>(requestDto2);
        responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getId()).isEqualTo(id);
        assertThat(all.get(0).getApiKey()).isEqualTo("admin");
        assertThat(all.get(0).getSecretKey()).isEqualTo("admin");
    }

    @Test
    public void findByIdTest() throws Exception{
        String id = "admin";

        //UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).password(password).apiKey("key").secretKey("key").build();
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder().id(id).password("admin").build();

        String url = "http://localhost:" + port + "/user/save";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        url = "http://localhost:" + port + "/user/findById/admin";

        responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("admin");
    }

     */
}
