//package taka.takaspring.Member.service;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import taka.takaspring.Member.db.UserEntity;
//import taka.takaspring.Member.db.UserRepository;
//import taka.takaspring.Member.dto.UserInfoDto;
//import taka.takaspring.Membership.exception.UserEntityNotFoundException;
//
//@DataJpaTest
//@Import({UserInfoService.class, BCryptPasswordEncoder.class})
//class UserInfoServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @InjectMocks
//    private UserInfoService userInfoService;
//
//    private UserEntity buildMockUser() {
//        return UserEntity.builder()
//                .email("choki@korea.ac.kr")
//                .password("Password123!")
//                .name("김초키")
//                .major("컴퓨터학과")
//                .studentNum("2019778899")
//                .phoneNumber("01012345678")
//                .build();
//    }
//
//    @Test
//    void findUserById_UserExists_ReturnsUserInfoResponse() {
//        // Given
//        Long userId = 1L;
//        UserEntity mockUser = buildMockUser();
//        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
//
//        // When
//        UserInfoDto.UserInfoResponse response = userInfoService.findUserById(userId);
//
//        // Then
//        assertNotNull(response);
//        verify(userRepository).findById(userId);
//    }
//
//    @Test
//    void findUserById_UserNotFound_ThrowsException() {
//        // Given
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());
//
//        // When & Then
//        assertThrows(UserEntityNotFoundException.class, () -> userInfoService.findUserById(userId));
//    }
//
//    @Test
//    void updateUser_WithPassword_EncodesPasswordAndUpdatesUser() {
//        // Given
//        Long userId = 1L;
//        UserEntity mockUser = buildMockUser();
//        UserInfoDto.UserInfoRequest request = new UserInfoDto.UserInfoRequest("new password", "김초키", "컴퓨터학과", "2019778899", "01012345678");
//
//        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
//        when(bCryptPasswordEncoder.encode("new password")).thenReturn("encoded password");
//
//        // When
//        UserInfoDto.UserInfoResponse response = userInfoService.updateUser(userId, request);
//
//        // Then
//        assertNotNull(response);
//        verify(bCryptPasswordEncoder).encode("new password");
//        verify(userRepository).save(mockUser);
//        assertEquals("김초키", mockUser.getName());
//        assertEquals("컴퓨터학과", mockUser.getMajor());
//        assertEquals("2019778899", mockUser.getStudentNum());
//        assertEquals("01012345678", mockUser.getPhoneNumber());
//        assertEquals("encoded password", mockUser.getPassword());
//    }
//
//    @Test
//    void updateUser_WithoutPassword_UpdatesUserWithoutEncodingPassword() {
//        // Given
//        Long userId = 1L;
//        UserEntity mockUser = buildMockUser();
//        UserInfoDto.UserInfoRequest request = new UserInfoDto.UserInfoRequest(null, "김초키", "컴퓨터학과", "2019778899", "01012345678");
//
//        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
//
//        // When
//        UserInfoDto.UserInfoResponse response = userInfoService.updateUser(userId, request);
//
//        // Then
//        assertNotNull(response);
//        verify(bCryptPasswordEncoder, never()).encode(anyString());
//        verify(userRepository).save(mockUser);
//        assertEquals("김초키", mockUser.getName());
//        assertEquals("컴퓨터학과", mockUser.getMajor());
//        assertEquals("2019778899", mockUser.getStudentNum());
//        assertEquals("01012345678", mockUser.getPhoneNumber());
//        assertNull(mockUser.getPassword());
//    }
//}
