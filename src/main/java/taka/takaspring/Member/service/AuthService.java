package taka.takaspring.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
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

    public void sendVerificationCode(String email) {
        // 6자리 인증번호 생성
        String verificationCode = UUID.randomUUID().toString().substring(0, 6);
        verifyMap.put(email, verificationCode);

        String subject = "[taka] 회원가입 인증코드 발송";
        String message = "회원가입 인증코드: " + verificationCode;

        emailService.sendSimpleMessage(email, subject, message);
    }

    // 프론트단에서 사용자가 확인차 입력한 verification code와 sendVerificationCode에서 생성한 verificationCode가 일치하는지 확인하는 로직
    public boolean verifyCode(String email, String code) {
        String storedCode = verifyMap.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            verifyMap.remove(email); // 인증이 완료되면 인증번호를 제거
            return true;
        }
        return false;
    }

    @Transactional
    public UserEntity signUp(SignupDto.SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (!verifyCode(request.getEmail(), request.getVerificationCode())) {
            throw new IllegalArgumentException("회원가입 인증코드가 틀렸습니다.");
        }
        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        UserEntity joinUser = UserEntity.builder()
                .email(request.getEmail())
                .password(encPassword)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(USER)
                .build();

        return userRepository.save(joinUser);
    }
}
