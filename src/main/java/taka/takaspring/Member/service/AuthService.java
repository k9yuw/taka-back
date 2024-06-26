package taka.takaspring.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserEntity signUp(UserEntity user) {

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        UserEntity joinUser = UserEntity.builder()
                .userId(user.getUserId())
                .password(encPassword)
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(parent.getEmail())
                .build();

        return userRepository.save(joinUser);
    }
}