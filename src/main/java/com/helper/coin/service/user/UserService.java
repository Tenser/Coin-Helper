package com.helper.coin.service.user;

import com.helper.coin.controller.dto.*;
import com.helper.coin.domain.user.User;
import com.helper.coin.domain.user.UserRepository;
import com.helper.coin.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponseDto save(UserSaveRequestDto requestDto){
        userRepository.save(requestDto.toEntity());
        //System.out.println(requestDto.getId() + requestDto.getPassword());
        return new UserResponseDto(userRepository.findByUserId(requestDto.getId()));
    }

    @Transactional
    public UserTokenResponseDto login(UserLoginRequestDto requestDto){
        User user = userRepository.findByUserId(requestDto.getId());
        if (user != null && user.getIsOn() == 0 && user.isSamePassword(requestDto.getPassword())) {
            user.on();
            return new UserTokenResponseDto("OK", jwtTokenProvider.createToken(user.getId(), new ArrayList<String>()), jwtTokenProvider.createRefreshToken());
        }

        return new UserTokenResponseDto("NO", "NO", "NO");
    }

    @Transactional
    public UserIsOkResponseDto logout(UserLoginRequestDto requestDto){
        User user = userRepository.findByUserId(requestDto.getId());
        if (user != null && user.getIsOn() == 1){
            user.off();
            return new UserIsOkResponseDto("OK");
        }
        return new UserIsOkResponseDto("NO");
    }

    @Transactional
    public UserIsOkResponseDto idCheck(String id){
        User user = userRepository.findByUserId(id);
        System.out.println(id);
        if (user == null) return new UserIsOkResponseDto("OK");
        return new UserIsOkResponseDto("NO");
    }

    @Transactional
    public UserIsOkResponseDto updatePassword(String id, UserUpdatePasswordRequestDto requestDto){
        User user = userRepository.findByUserId(id);
        if (user != null){
            user.updatePassword(requestDto.getPassword());
            return new UserIsOkResponseDto("OK");
        }
        return new UserIsOkResponseDto("NO");
    }

    @Transactional
    public UserIsOkResponseDto updateInform(String id, UserUpdateInformRequestDto requestDto){
        User user = userRepository.findByUserId(id);
        if (user != null){
            user.updateInform(requestDto.getName(), requestDto.getApiKey(), requestDto.getSecretKey());
            return new UserIsOkResponseDto("OK");
        }
        return new UserIsOkResponseDto("NO");
    }

    @Transactional
    public UserResponseDto findById(String id){
        User user = userRepository.findByUserId(id);
        return new UserResponseDto(user);
    }

    @Transactional
    public UserTokenResponseDto refresh(String id, String accessToken, String refreshToken){
        if(!jwtTokenProvider.validateToken(accessToken)){
            if(jwtTokenProvider.validateToken(refreshToken)){
                return new UserTokenResponseDto("OK", jwtTokenProvider.createToken(id, new ArrayList<String>()), refreshToken);
            }else{
                return new UserTokenResponseDto("refreshToken expired", accessToken, refreshToken);
            }
        }
        return new UserTokenResponseDto("not expired", accessToken, refreshToken);
    }

    @Transactional
    public int showLevel(String id){
        User user = userRepository.findByUserId(id);
        if (user != null){
            return user.getLevel();
        }
        return 0;
    }

    @Transactional
    public UserIsOkResponseDto changeLevel(String id, int level){
        User user = userRepository.findByUserId(id);
        if (user != null){
            user.changeLevel(level);
            return new UserIsOkResponseDto("OK");
        }
        return new UserIsOkResponseDto("NO");
    }

    @Transactional
    public List<UserResponseDto> findAll(){
        List<User> users = userRepository.findAll();
        List<UserResponseDto> responseDtos = new ArrayList<>();
        for (User user: users){
            responseDtos.add(new UserResponseDto(user));
        }
        return responseDtos;
    }
}
