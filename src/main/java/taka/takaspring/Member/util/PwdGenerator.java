package taka.takaspring.Member.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PwdGenerator {

    public String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
