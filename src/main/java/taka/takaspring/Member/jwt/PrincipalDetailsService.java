package taka.takaspring.Member.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public PrincipalDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    // 패스워드는 스프링 시큐리티에서 알아서 체킹하니까 신경 쓸 필요 없다.
    // 따라서 loginId만 있는지 없는지 확인해주면 된다.
    // 리턴이 잘 되면 내부적으로 UserDetails 타입 세션으로 자동으로 만든다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            // DB에서 조회
            UserEntity user = userRepository.findByEmail(email);

            if (user == null) {
                // 사용자를 찾지 못한 경우 UsernameNotFoundException을 던진다.
                throw new UsernameNotFoundException("User not found with email: " + email);
            } else {
                return new PrincipalDetails(user);
            }
        } catch (UsernameNotFoundException ex) {
            System.out.println("UsernameNotFoundException: " + ex.getMessage());
            throw ex;
        }
    }

}