//package taka.takaspring.Member.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import taka.takaspring.Member.db.UserEntity;
//import taka.takaspring.Member.db.UserRepository;
//import taka.takaspring.Member.db.enums.RoleType;
//import taka.takaspring.Member.service.EmailService;
//import taka.takaspring.Member.service.ForgotPwdService;
//import taka.takaspring.Member.util.PwdGenerator;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//class ForgotPwdServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private EmailService emailService;
//
//    @Mock
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Mock
//    private PwdGenerator pwdGenerator;
//
//    @InjectMocks
//    private ForgotPwdService forgotPwdService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void sendTemporaryPassword_shouldSendEmailAndUpdateUserPassword_whenUserExists() {
//
//        String email = "choki@example.com";
//        String generatedPassword = "tempPwd";
//        String encryptedPassword = "encryptedTempPwd";
//
//        UserEntity user = UserEntity.builder()
//                .id(1L)
//                .email(email)
//                .password("oldPassword")
//                .name("김초키")
//                .major("컴퓨터학과")
//                .studentNum("2019778899")
//                .phoneNumber("01012345678")
//                .role(RoleType.USER)
//                .build();
//
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//        when(pwdGenerator.generateTemporaryPassword()).thenReturn(generatedPassword);
//        when(bCryptPasswordEncoder.encode(generatedPassword)).thenReturn(encryptedPassword);
//
//        String resultEmail = forgotPwdService.sendTemporaryPassword(email);
//
//        // 이메일이 올바른 주소로 전송 되었는지 확인
//        assertEquals(email, resultEmail);
//
//        // 사용자의 비밀번호가 새로 생성된 비밀번호로 업데이트되었는지 확인
//        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
//        verify(userRepository).save(userCaptor.capture());
//        UserEntity savedUser = userCaptor.getValue();
//        assertEquals(encryptedPassword, savedUser.getPassword());
//
//        // 이메일 내용 확인
//        ArgumentCaptor<String> toCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
//        verify(emailService).sendSimpleMessage(toCaptor.capture(), subjectCaptor.capture(), textCaptor.capture());
//
//        // 이메일 제목 확인
//        assertEquals(email, toCaptor.getValue());
//        assertEquals("[taka] 임시 비밀번호 발송", subjectCaptor.getValue());
//
//        // 이메일 본문 확인
//        String capturedText = textCaptor.getValue();
//        assertTrue(capturedText.contains("임시비밀번호: " + generatedPassword));
//    }
//
//
//    @Test
//    void sendTemporaryPassword_shouldThrowException_whenUserDoesNotExist() {
//
//        String email = "notexisting@example.com";
//        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
//            forgotPwdService.sendTemporaryPassword(email);
//        });
//
//        assertEquals("User not found with email: " + email, exception.getMessage());
//    }
//}
