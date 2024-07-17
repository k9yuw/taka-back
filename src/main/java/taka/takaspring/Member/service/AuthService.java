package taka.takaspring.Member.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.exception.EmailDuplicateException;
import taka.takaspring.Member.exception.InvalidVerificationCodeException;
import taka.takaspring.Member.exception.StudentNumberDuplicateException;
import taka.takaspring.Member.exception.VerificationCodeSendingFailureException;
import taka.takaspring.Member.service.dto.SignupDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static taka.takaspring.Member.db.enums.RoleType.USER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    private Map<String, String> verifyMap = new HashMap<>(); // 이메일과 인증번호를 저장하는 map
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void sendVerificationCode(String email) {
        try {
            // 6자리 인증번호 생성
            String verificationCode = UUID.randomUUID().toString().substring(0, 6);
            verifyMap.put(email, verificationCode);

            // 이메일 전송
            String subject = "[taka] 회원가입 인증번호 발송";
            String message = "taka 회원가입 인증번호입니다." + System.lineSeparator() + "인증번호: "+ verificationCode;
            emailService.sendSimpleMessage(email, subject, message);

            logger.info("회원가입 인증번호 전송 완료: {} - {}", email, verificationCode);

        } catch (Exception e) {
            String errorMessage = String.format("회원가입 인증번호 전송 오류 발생: email=%s, error=%s", email, e.getMessage());
            logger.error(errorMessage, e);
            throw new VerificationCodeSendingFailureException(errorMessage);
        }
    }

    // 프론트단에서 사용자가 확인차 입력한 verification code와 sendVerificationCode에서 생성한 verificationCode가 일치하는지 확인하는 로직
    public boolean verifyCode(String email, String code) {
        String storedCode = verifyMap.get(email);
//        logger.info("저장된 코드 {}: {}", email, storedCode);

        if (storedCode != null && storedCode.equals(code)) {
            logger.info("인증번호 검증 성공: email={}, code={}", email, code);
            return true;
        } else {
            String message = String.format("인증번호 검증 실패: email=%s, code=%s, storedCode=%s", email, code, storedCode);
            logger.warn(message);
            throw new InvalidVerificationCodeException(message);
        }
    }

    @Transactional
    public UserEntity signUp(SignupDto.SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            String message = String.format("회원가입 중 이메일 중복 예외 발생: email=%s", request.getEmail());
            logger.warn(message);
            throw new EmailDuplicateException(message);
        }

        if (userRepository.existsByStudentNum(request.getStudentNum())) {
            String message = String.format("회원가입 중 학번 중복 예외 발생: studentNum=%s", request.getStudentNum());
            logger.warn(message);
            throw new StudentNumberDuplicateException(message);
        }

        if (!verifyCode(request.getEmail(), request.getVerificationCode())) {
            String message = String.format("회원가입 중 인증번호 검증 실패: email=%s, verificationCode=%s", request.getEmail(), request.getVerificationCode());
            logger.warn(message);
            throw new InvalidVerificationCodeException(message);
        }

        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        UserEntity joinUser = UserEntity.builder()
                .email(request.getEmail())
                .password(encPassword)
                .name(request.getName())
                .major(request.getMajor())
                .studentNum(request.getStudentNum())
                .phoneNumber(request.getPhoneNumber())
                .role(USER)
                .build();

        return userRepository.save(joinUser);
    }
}
