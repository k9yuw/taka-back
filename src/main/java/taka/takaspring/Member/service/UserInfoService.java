package taka.takaspring.Member.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.controller.AuthController;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.db.enums.RoleType;
import taka.takaspring.Member.service.dto.UserInfoDto;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public UserInfoDto.UserInfoResponse findUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserEntityNotFoundException("사용자 정보 조회 요청 userId : " + userId));
        return new UserInfoDto.UserInfoResponse(user);
    }

    public UserInfoDto.UserInfoResponse updateUser(Long userId, UserInfoDto.UserInfoRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserEntityNotFoundException("사용자 정보 조회 요청 userId : " + userId));

        String encPassword = null;
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            encPassword = bCryptPasswordEncoder.encode(request.getPassword());
        }

        user.update(encPassword, request.getName(), request.getMajor(), request.getStudentNum(), request.getPhoneNumber());
        userRepository.save(user);
        UserInfoDto.UserInfoResponse response = new UserInfoDto.UserInfoResponse(user);

        return response;
    }


}