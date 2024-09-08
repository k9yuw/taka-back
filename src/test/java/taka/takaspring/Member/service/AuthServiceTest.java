//package taka.takaspring.Member.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import taka.takaspring.Member.db.UserEntity;
//import taka.takaspring.Member.db.UserRepository;
//import taka.takaspring.Member.dto.SignupDto;
//import taka.takaspring.Member.exception.EmailDuplicateException;
//import taka.takaspring.Member.exception.InvalidVerificationCodeException;
//import taka.takaspring.Member.exception.StudentNumberDuplicateException;
//import taka.takaspring.common.exception.ErrorCode;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AuthServiceTest {
//
//    @InjectMocks
//    private AuthService authService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Mock
//    private EmailService emailService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void sendVerificationCode_shouldSendEmail() {
//        String email = "choki@korea.ac.kr";
//
//        authService.sendVerificationCode(email);
//
//        verify(emailService, times(1)).sendSimpleMessage(eq(email), anyString(), anyString());
//    }
//
//    @Test
//    void verifyCode_shouldReturnTrue_whenCodeMatches() {
//        AuthService realAuthService = new AuthService(userRepository, bCryptPasswordEncoder, emailService);
//        AuthService spyAuthService = spy(realAuthService);
//        doReturn("123456").when(spyAuthService).generateVerificationCode();
//
//        String email = "choki@korea.ac.kr";
//        String code = "123456";
//
//        spyAuthService.sendVerificationCode(email);
//        boolean result = spyAuthService.verifyCode(email, code);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void verifyCode_shouldThrowException_whenCodeDoesNotMatch() {
//        String email = "choki@korea.ac.kr";
//        String code = "123456";
//        authService.sendVerificationCode(email);
//
//        assertThrows(InvalidVerificationCodeException.class, () -> {
//            authService.verifyCode(email, ErrorCode.INVALID_VERIFICATION_CODE.toString());
//        });
//    }
//
//    @Test
//    void signUp_shouldThrowEmailDuplicateException_whenEmailAlreadyExists() {
//        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder()
//                .email("choki@korea.ac.kr")
//                .password("Password123!")
//                .verificationCode("123456")
//                .name("김초키")
//                .major("컴퓨터학과")
//                .studentNum("2019778899")
//                .phoneNumber("01012345678")
//                .build();
//
//        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
//
//        assertThrows(EmailDuplicateException.class, () -> {
//            authService.signUp(request);
//        });
//    }
//
//    @Test
//    void signUp_shouldThrowStudentNumberDuplicateException_whenStudentNumberAlreadyExists() {
//        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder()
//                .email("choki@korea.ac.kr")
//                .password("Password123!")
//                .verificationCode("123456")
//                .name("김초키")
//                .major("컴퓨터학과")
//                .studentNum("2019778899")
//                .phoneNumber("01012345678")
//                .build();
//
//        when(userRepository.existsByStudentNum(request.getStudentNum())).thenReturn(true);
//
//        assertThrows(StudentNumberDuplicateException.class, () -> {
//            authService.signUp(request);
//        });
//    }
//
//    @Test
//    void signUp_shouldSaveUser_whenValidRequest() {
//        SignupDto.SignupRequest request = SignupDto.SignupRequest.builder()
//                .email("choki@korea.ac.kr")
//                .password("Password123!")
//                .verificationCode("123456")
//                .name("김초키")
//                .major("컴퓨터학과")
//                .studentNum("2019778899")
//                .phoneNumber("01012345678")
//                .build();
//
//        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
//        when(userRepository.existsByStudentNum(request.getStudentNum())).thenReturn(false);
//        when(bCryptPasswordEncoder.encode(request.getPassword())).thenReturn("encryptedPassword");
//
//        AuthService spyAuthService = spy(authService);
//
//        // spyAuthService의 verifyCode 메서드를 mocking하여 항상 true를 반환하도록 설정
//        doReturn(true).when(spyAuthService).verifyCode(request.getEmail(), request.getVerificationCode());
//
//        SignupDto.SignUpResponse response = spyAuthService.signUp(request);
//
//        // UserRepository의 save 메서드가 한 번 호출되었는지 확인
//        verify(userRepository, times(1)).save(any(UserEntity.class));
//
//        assertEquals(request.getEmail(), response.getEmail());
//        assertEquals(request.getName(), response.getName());
//        assertEquals(request.getMajor(), response.getMajor());
//        assertEquals(request.getStudentNum(), response.getStudentNum());
//        assertEquals(request.getPhoneNumber(), response.getPhoneNumber());
//    }
//}
