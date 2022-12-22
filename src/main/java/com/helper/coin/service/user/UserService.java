package com.helper.coin.service.user;

import com.helper.coin.controller.dto.*;
import com.helper.coin.domain.user.User;
import com.helper.coin.domain.user.UserRepository;
import com.helper.coin.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponseDto save(UserSaveRequestDto requestDto){
        userRepository.save(requestDto.toEntity());
        System.out.println(requestDto.getId() + requestDto.getPassword());
        return new UserResponseDto(userRepository.findByUserId(requestDto.getId()));
    }

    @Transactional
    public UserTokenResponseDto login(UserLoginRequestDto requestDto){
        User user = userRepository.findByUserId(requestDto.getId());
        if (user != null && user.isSamePassword(requestDto.getPassword())) return new UserTokenResponseDto("OK", jwtTokenProvider.createToken(user.getId(), new ArrayList<String>()));
        return new UserTokenResponseDto("NO", "NO");
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


}
