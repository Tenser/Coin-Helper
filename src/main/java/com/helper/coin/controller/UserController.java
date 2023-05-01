package com.helper.coin.controller;

import com.helper.coin.controller.dto.*;
import com.helper.coin.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user/save")
    public int save(@RequestBody UserSaveRequestDto requestDto){
        return userService.save(requestDto);
    }

    @PostMapping("/user/login")
    public UserTokenResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        //System.out.println(requestDto.getId());
        return userService.login(requestDto);
    }

    @PostMapping("/user/logout")
    public UserIsOkResponseDto logout(@RequestBody UserLoginRequestDto requestDto) {
        //System.out.println(requestDto.getId());
        return userService.logout(requestDto);
    }

    @PostMapping("user/refresh/{id}")
    public UserTokenResponseDto login(@PathVariable String id, @RequestHeader String accessToken, @RequestHeader String refreshToken){
        return userService.refresh(id, accessToken, refreshToken);
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

    @PostMapping("/user/showLevel/{id}")
    public int showLevel(@PathVariable String id) {return userService.showLevel(id);}

    @PostMapping("/user/changeLevel/{id}/{level}")
    public UserIsOkResponseDto changeLevel(@PathVariable String id, @PathVariable int level){
        return userService.changeLevel(id, level);
    }

    @PostMapping("/user/findAll")
    public List<UserResponseDto> findAll(){
        return userService.findAll();
    }
}
