package taka.takaspring.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static taka.takaspring.Member.db.enums.RoleType.USER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.service.AuthService;
import taka.takaspring.Member.service.dto.SignupDto;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp_ShouldCreateNewUser() {
        // 정상적인 회원가입 테스트

        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder().
                    .email("testuser@example.com")
                    .password(bCryptPasswordEncoder.encode("Password1!"))
                    .name("김초키")
                    .phoneNumber("01012345678")
                    .role(USER)
                    .build();

        when(bCryptPasswordEncoder.encode(request.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity savedUser = authService.signUp(request);

        verify(userRepository).save(any(UserEntity.class));
        assertEquals("encryptedPassword", savedUser.getPassword());
        assertEquals("Test User", savedUser.getName());
        assertEquals("01012345678", savedUser.getPhoneNumber());
        assertEquals("testuser@example.com", savedUser.getEmail());
    }

}