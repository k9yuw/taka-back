//package taka.takaspring.Member.config;
//
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//import org.springframework.security.test.context.support.WithSecurityContext;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import taka.takaspring.Member.db.UserEntity;
//import taka.takaspring.Member.jwt.PrincipalDetails;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//
//public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
//    @Override
//    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//
//        UserEntity userEntity = UserEntity.builder()
//                .email(customUser.username())
//                .password("password")
//                .role(customUser.role())
//                .build();
//
//        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(principalDetails, "password", principalDetails.getAuthorities()));
//
//        return context;
//    }
//}
//
