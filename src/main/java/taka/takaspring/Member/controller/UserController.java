package taka.takaspring.Member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Member.service.dto.UserInfoDto;
import taka.takaspring.Member.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info/{id}")
    public ResponseEntity<UserInfoDto.UserInfoResponse> getParentById(@PathVariable Long id) {

        UserInfoDto.UserInfoResponse response = userService.findUserById(id);

        if (apiResponse.getData() != null) {
            apiResponse.setStatus(Api.SUCCESS_STATUS);
            apiResponse.setMessage("해당 회원 정보 조회 성공");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse.setStatus(Api.ERROR_STATUS);
            apiResponse.setMessage("해당 회원을 찾을 수 없습니다.");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/info/{id}")
    public ResponseEntity<UserInfoDto.UserInfoResponse> updateUser(@PathVariable Long id,
                                                @RequestBody UserInfoDto.UserInfoRequest request) {

        try {
            UserInfoDto.UserInfoResponse response = UserService.updateUser(id, request);
            return ResponseEntity.ok()
                    .body(response);
        if (response != null) {

            return ResponseEntity.ok()
                    .body(response);
        } else {
            apiResponse.setStatus(Api.ERROR_STATUS);
            apiResponse.setMessage("회원 정보 수정 실패");
            return new ResponseEntity<>;
        }
    }
