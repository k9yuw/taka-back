package taka.takaspring.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        String email = "choki@korea.ac.kr"; // 실제 존재하는 이메일
        UserEntity user = userRepository.findByEmail(email);
        Assertions.assertNotNull(user, "User should not be null");
        Assertions.assertEquals(email, user.getEmail(), "Email should match");
    }
}
