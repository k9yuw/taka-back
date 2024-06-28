package taka.takaspring.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.service.dto.SignupRequest;
import taka.takaspring.Member.service.dto.SignupResponse;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserEntity signUp(SignupRequest request) {

        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        UserEntity joinUser = UserEntity.builder()
                .userId(request.getUserId())
                .password(encPassword)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .build();

        return userRepository.save(joinUser);
    }


    private UserEntity toEntity(SignupRequest request) {

        UserEntity user = UserEntity.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .build();

        return user;
    }

    private SignupResponse toDto(UserEntity user) {
        SignupResponse response = new SignupResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }
}