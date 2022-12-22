package com.helper.coin.controller;

import com.helper.coin.controller.dto.*;
import com.helper.coin.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user/save")
    public UserResponseDto save(@RequestBody UserSaveRequestDto requestDto){
        return userService.save(requestDto);
    }

    @PostMapping("/user/login")
    public UserTokenResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        //System.out.println(requestDto.getId());
        return userService.login(requestDto);
    }

    @GetMapping("/user/idCheck/{id}")
    public UserIsOkResponseDto idCheck(@PathVariable String id) { return userService.idCheck(id); }

    @PutMapping("/user/updatePassword/{id}")
    public UserIsOkResponseDto updatePassword(@PathVariable String id, @RequestBody UserUpdatePasswordRequestDto requestDto){
        return userService.updatePassword(id, requestDto);
    }

    @PutMapping("/user/updateInform/{id}")
    public UserIsOkResponseDto updateInform(@PathVariable String id, @RequestBody UserUpdateInformRequestDto requestDto){
        return userService.updateInform(id, requestDto);
    }

    @GetMapping("/user/findById/{id}")
    public UserResponseDto findById(@PathVariable String id){
        return userService.findById(id);
    }
}
