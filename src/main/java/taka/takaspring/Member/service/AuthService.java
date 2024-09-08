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
import taka.takaspring.Member.dto.SignupDto;

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

    public boolean checkIfUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void sendVerificationCode(String email) {
        if (checkIfUserExists(email)) {
            throw new EmailDuplicateException("이미 회원가입이 되어 있는 이메일입니다.");
        }

        try {
            String verificationCode = generateVerificationCode();
            verifyMap.put(email, verificationCode);

            // 이메일 전송
            String subject = "[taka] 회원가입 인증번호 발송";
            String message = "taka 회원가입 인증번호입니다." + System.lineSeparator() + "인증번호: " + verificationCode;
            emailService.sendSimpleMessage(email, subject, message);

            logger.info("회원가입 인증번호 전송 완료: {} - {}", email, verificationCode);

        } catch (Exception e) {
            String errorMessage = String.format("회원가입 인증번호 전송 오류 발생: email=%s, error=%s", email, e.getMessage());
            logger.error(errorMessage, e);
            throw new VerificationCodeSendingFailureException(errorMessage);
        }
    }

    protected String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = verifyMap.get(email);

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
    public SignupDto.SignUpResponse signUp(SignupDto.SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplicateException("이미 회원가입이 되어 있는 이메일입니다.");
        }

        if (userRepository.existsByStudentNum(request.getStudentNum())) {
            throw new StudentNumberDuplicateException("이미 해당 학번으로 회원가입이 되어 있습니다.");
        }

        if (!verifyCode(request.getEmail(), request.getVerificationCode())) {
            throw new InvalidVerificationCodeException("인증번호가 일치하지 않습니다.");
        }

        String encPassword = bCryptPasswordEncoder.encode(request.getPassword());

        UserEntity joinUser = UserEntity.builder()
                .email(request.getEmail())
                .password(encPassword)
                .name(request.getName())
                .major(request.getMajor())
                .studentNum(request.getStudentNum())
                .phoneNumber(request.getPhoneNumber())
                .role(USER)
                .build();

        userRepository.save(joinUser);

        return SignupDto.SignUpResponse.builder()
                .email(request.getEmail())
                .name(request.getName())
                .major(request.getMajor())
                .studentNum(request.getStudentNum())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
