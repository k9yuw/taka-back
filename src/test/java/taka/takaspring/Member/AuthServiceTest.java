package taka.takaspring.Member;

import static org.junit.jupiter.api.Assertions.*;
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
import taka.takaspring.Member.service.EmailService;
import taka.takaspring.Member.service.dto.SignupDto;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. 이메일 형식이 정상적이고 (@korea.ac.kr) / 인증번호가 제대로 전송되어 올바르게 인증번호를 입력해서 회원가입이 잘 진행된 경우
    @Test
    void signUp_ShouldCreateNewUser_WhenEmailAndCodeAreValid() {
        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder()
                .email("test@korea.ac.kr")
                .verificationCode("123456")
                .password("Password1!")
                .name("김초키")
                .phoneNumber("01012345678")
                .build();

        // 인증 코드 검증을 통과하도록 설정
        authService.sendVerificationCode(request.getEmail());
        authService.verifyCode(request.getEmail(), "123456");

        when(bCryptPasswordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(UserEntity.builder()
                .email("test@korea.ac.kr")
                .password("encodedPassword")
                .name("김초키")
                .phoneNumber("01012345678")
                .role(USER)
                .build());

        UserEntity savedUser = authService.signUp(request);

        verify(userRepository).save(any(UserEntity.class));
        assertEquals("test@korea.ac.kr", savedUser.getEmail());
    }

    // 2. 이메일 형식이 정상적이지만, 인증번호를 틀리게 입력한 경우
    @Test
    void signUp_ShouldThrowException_WhenVerificationCodeIsInvalid() {
        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder()
                .email("test@korea.ac.kr")
                .verificationCode("654321")
                .password("Password1!")
                .name("김초키")
                .phoneNumber("01012345678")
                .build();

        // 인증 코드 검증을 실패하도록 설정
        authService.sendVerificationCode(request.getEmail());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.signUp(request);
        });

        assertEquals("회원가입 인증코드가 틀렸습니다.", exception.getMessage());
    }

    // 3. 이메일 형식이 맞지 않는 경우
    @Test
    void signUp_ShouldThrowException_WhenEmailIsInvalid() {
        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder()
                .email("test@notkorea.ac.kr")
                .verificationCode("123456")
                .password("Password1!")
                .name("김초키")
                .phoneNumber("01012345678")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.signUp(request);
        });

        assertEquals("올바르지 않은 이메일 형식입니다.", exception.getMessage());
    }

    // 4. 이메일도 정상적이고 인증번호도 인증했지만 비밀번호 형식이 안맞는 경우
    @Test
    void signUp_ShouldThrowException_WhenPasswordIsInvalid() {
        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder()
                .email("test@korea.ac.kr")
                .verificationCode("123456")
                .password("invalid")
                .name("김초키")
                .phoneNumber("01012345678")
                .build();

        // 인증 코드 검증을 통과하도록 설정
        authService.sendVerificationCode(request.getEmail());
        authService.verifyCode(request.getEmail(), "123456");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.signUp(request);
        });

        assertEquals("비밀번호는 영문 대, 소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.", exception.getMessage());
    }

    // 5. 중복된 이메일이 존재하는 경우
    @Test
    void signUp_ShouldThrowException_WhenEmailAlreadyExists() {
        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder()
                .email("test@korea.ac.kr")
                .verificationCode("123456")
                .password("Password1!")
                .name("김초키")
                .phoneNumber("01012345678")
                .build();

        when(userRepository.existsByEmail("test@korea.ac.kr")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.signUp(request);
        });

        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }
}