package taka.takaspring.Member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.util.PwdGenerator;

import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPwdService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PwdGenerator pwdGenerator;
    private static final Logger logger = LoggerFactory.getLogger(ForgotPwdService.class);

    public ForgotPwdService(UserRepository userRepository, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder, PwdGenerator pwdGenerator) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.pwdGenerator = pwdGenerator;
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


