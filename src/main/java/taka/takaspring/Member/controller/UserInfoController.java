package taka.takaspring.Member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Member.service.dto.UserInfoDto;
import taka.takaspring.Member.service.UserInfoService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    // 회원정보 조회
    @GetMapping("/info/{id}")
    public ResponseEntity<UserInfoDto.UserInfoResponse> getUserInfoById(@PathVariable Long id) {
        UserInfoDto.UserInfoResponse response = userInfoService.findUserById(id);
        return ResponseEntity.ok().body(response);
    }

    // 회원정보 수정
    @PatchMapping("/info/{id}")
    public ResponseEntity<UserInfoDto.UserInfoResponse> updateUserInfo(@PathVariable Long id, @RequestBody UserInfoDto.UserInfoRequest request) {
        UserInfoDto.UserInfoResponse response = userInfoService.updateUser(id, request);
        return ResponseEntity.ok().body(response);
    }
}