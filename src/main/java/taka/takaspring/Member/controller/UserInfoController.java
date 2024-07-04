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

    @GetMapping("/info/{id}")
    public ResponseEntity<UserInfoDto.UserInfoResponse> getUserInfoById(@PathVariable Long id) {
        try {
            UserInfoDto.UserInfoResponse response = userInfoService.findUserById(id);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/info/{id}")
    public ResponseEntity<UserInfoDto.UserInfoResponse> updateUserInfo(@PathVariable Long id, @RequestBody UserInfoDto.UserInfoRequest request) {
        try {
            UserInfoDto.UserInfoResponse response = userInfoService.updateUser(id, request);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}