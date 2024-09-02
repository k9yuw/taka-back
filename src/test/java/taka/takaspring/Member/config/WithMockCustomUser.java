package taka.takaspring.Member.config;

import org.springframework.security.test.context.support.WithSecurityContext;
import taka.takaspring.Member.db.enums.RoleType;

@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String username() default "choki@korea.ac.kr";
    RoleType role() default RoleType.USER;
}
