package taka.takaspring.Member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.exception.EmailDuplicateException;
import taka.takaspring.Member.exception.InvalidVerificationCodeException;
import taka.takaspring.Member.exception.VerificationCodeSendingFailureException;
import taka.takaspring.Member.util.PwdGenerator;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPwdService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PwdGenerator pwdGenerator;
    private static final Logger logger = LoggerFactory.getLogger(ForgotPwdService.class);

    private Map<String, String> verifyMap = new HashMap<>();

    public ForgotPwdService(UserRepository userRepository, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder, PwdGenerator pwdGenerator) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.pwdGenerator = pwdGenerator;
    }

    public boolean checkIfUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void sendVerificationCode(String email) {
        if (!checkIfUserExists(email)) {
            throw new UserEntityNotFoundException("존재하지 않는 회원입니다.");
        }

        try {
            String verificationCode = generateVerificationCode();
            verifyMap.put(email, verificationCode);

            // 이메일 전송
            String subject = "[taka] 비밀번호 찾기 인증번호 발송";
            String message = "taka 비밀번호 찾기 인증번호입니다." + System.lineSeparator() + "인증번호: " + verificationCode;
            emailService.sendSimpleMessage(email, subject, message);

            logger.info("비밀번호 찾기 인증번호 전송 완료: {} - {}", email, verificationCode);

        } catch (Exception e) {
            String errorMessage = String.format("비밀번호 찾기 인증번호 전송 오류 발생: email=%s, error=%s", email, e.getMessage());
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
            logger.info("비밀번호 찾기 인증번호 검증 성공: email={}, code={}", email, code);
            return true;
        } else {
            String message = String.format("비밀번호 찾기 인증번호 검증 실패: email=%s, code=%s, storedCode=%s", email, code, storedCode);
            logger.warn(message);
            throw new InvalidVerificationCodeException(message);
        }
    }

    public String sendTemporaryPassword(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            String temporaryPassword = pwdGenerator.generateTemporaryPassword();
            String encPassword = encodePassword(temporaryPassword);

            UserEntity updatedUser = UserEntity.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(encPassword)
                    .name(user.getName())
                    .major(user.getMajor())
                    .studentNum(user.getStudentNum())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole())
                    .build();

            userRepository.save(updatedUser);

            String subject = "[taka] 임시 비밀번호 발송";
            String message = "요청하신 임시 비밀번호는 다음과 같습니다." + System.lineSeparator() + "임시비밀번호: " + temporaryPassword;
            emailService.sendSimpleMessage(email, subject, message);

            return email;
        } else {
            logger.warn("해당 이메일을 가진 사용자가 존재하지 않습니다: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}


