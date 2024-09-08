package taka.takaspring.Member.service;

import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.dto.VerifyUserDto;
import taka.takaspring.Member.jwt.JwtUtil;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;
import taka.takaspring.Member.exception.ExpiredTokenException;

@Service
public class VerifyUserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public VerifyUserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public VerifyUserDto.Response verifyUser(String accessToken) {
        // jwt 토큰 검증 (만료 여부 확인)
        if (jwtUtil.isExpired(accessToken)) {
            throw new ExpiredTokenException("토큰이 만료되었습니다.");
        }

        // jwt에서 유저 정보 추출
        String email = jwtUtil.getEmail(accessToken);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEntityNotFoundException("존재하지 않는 회원입니다."));

        return VerifyUserDto.Response.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .studentNum(user.getStudentNum())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}

