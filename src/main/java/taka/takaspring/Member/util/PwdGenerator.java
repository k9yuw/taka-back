package taka.takaspring.Member.util;

import java.util.UUID;

public class PwdGenerator {

    public String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
