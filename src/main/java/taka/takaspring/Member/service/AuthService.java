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

            logger.info("회원가입 인증번호 전송 완료 {}: {}", email, verificationCode);

        } catch (Exception e) {
            logger.info("회원가입 인증번호 전송에 실패했습니다: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 프론트단에서 사용자가 확인차 입력한 verification code와 sendVerificationCode에서 생성한 verificationCode가 일치하는지 확인하는 로직
    public boolean verifyCode(String email, String code) {
        String storedCode = verifyMap.get(email);
//        logger.info("저장된 코드 {}: {}", email, storedCode);

        if (storedCode != null && storedCode.equals(code)) {
//            verifyMap.remove(email); // 인증이 완료되면 인증번호를 제거
            return true;
        }
        return false;
    }

    @Transactional
    public UserEntity signUp(SignupDto.SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplicateException();
        }

        if () {
            throw new StudentNumberDuplicateException();
        }

        if (!verifyCode(request.getEmail(), request.getVerificationCode())) {
            throw new InvalidVerificationCodeException();
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
