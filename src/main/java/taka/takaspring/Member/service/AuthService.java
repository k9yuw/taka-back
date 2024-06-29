package taka.takaspring.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.service.dto.SignupDto;

import static taka.takaspring.Member.db.enums.RoleType.USER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserEntity signUp(SignupDto.SignupRequest request) {

        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        UserEntity joinUser = UserEntity.builder()
                .userId(request.getUserId())
                .password(encPassword)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .role(USER)
                .build();

        return userRepository.save(joinUser);
    }

}