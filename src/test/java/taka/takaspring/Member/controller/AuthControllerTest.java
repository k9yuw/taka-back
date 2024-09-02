package taka.takaspring.Member.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import taka.takaspring.Member.config.WithMockCustomUser;
import taka.takaspring.Member.db.enums.RoleType;
import taka.takaspring.Member.dto.SignupDto;
import taka.takaspring.Member.exception.InvalidVerificationCodeException;
import taka.takaspring.Member.jwt.JwtUtil;
import taka.takaspring.Member.jwt.RefreshRepository;
import taka.takaspring.Member.service.AuthService;
import taka.takaspring.TakaSpringApplication;
import taka.takaspring.config.SecurityConfig;
import taka.takaspring.config.SwaggerConfig;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = TakaSpringApplication.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private RefreshRepository refreshRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void sendVerificationCode_shouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/auth/send-verification-code")
                        .param("email", "choki@example.com")
                        .with(csrf()))  // CSRF 토큰 포함
                .andExpect(status().isOk());
    }

    @Test
    void verifyCode_shouldReturnOk() throws Exception {
        // AuthService의 verifyCode 메서드 Mocking
        String testEmail = "choki@example.com";
        String testCode = "123456";

        Mockito.when(authService.verifyCode(testEmail, testCode)).thenReturn(true);

        mockMvc.perform(post("/api/auth/verify-code")
                        .param("email", testEmail)
                        .param("verificationCode", testCode)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void verifyCode_shouldReturnBadRequestForInvalidCode() throws Exception {
        String testEmail = "choki@example.com";
        String invalidCode = "654321";  // 잘못된 코드

        Mockito.when(authService.verifyCode(testEmail, invalidCode))
                .thenThrow(new InvalidVerificationCodeException("Invalid code"));

        mockMvc.perform(post("/api/auth/verify-code")
                        .param("email", testEmail)
                        .param("verificationCode", invalidCode)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signup_shouldReturnOkWithResponse() throws Exception {
        // signUp 메서드가 예상한 응답을 반환하도록 mocking
        SignupDto.SignUpResponse mockResponse = SignupDto.SignUpResponse.builder()
                .email("choki@example.com")
                .name("김초키")
                .major("컴퓨터학과")
                .studentNum("2019778899")
                .phoneNumber("01012345678")
                .build();

        when(authService.signUp(any(SignupDto.SignupRequest.class))).thenReturn(mockResponse);

        // 요청 JSON 데이터
        String signupRequestJson = "{"
                + "\"email\":\"choki@example.com\","
                + "\"password\":\"Password123!\","
                + "\"name\":\"김초키\","
                + "\"major\":\"컴퓨터학과\","
                + "\"studentNum\":\"2019778899\","
                + "\"phoneNumber\":\"01012345678\","
                + "\"verificationCode\":\"123456\""
                + "}";

        // 요청 전송 및 검증
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("choki@example.com"))
                .andExpect(jsonPath("$.name").value("김초키"))
                .andExpect(jsonPath("$.major").value("컴퓨터학과"))
                .andExpect(jsonPath("$.studentNum").value("2019778899"))
                .andExpect(jsonPath("$.phoneNumber").value("01012345678"));
    }
}
