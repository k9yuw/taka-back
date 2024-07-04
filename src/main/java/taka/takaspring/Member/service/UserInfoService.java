package taka.takaspring.Member.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.controller.AuthController;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.service.dto.UserInfoDto;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public UserInfoDto.UserInfoResponse findUserById(Long id) {

        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return new UserInfoDto.UserInfoResponse(user);
        } else {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }
    }




    public UserInfoDto.UserInfoResponse updateUser(Long id, UserInfoDto.UserInfoRequest request) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();

            // 업데이트 로직
            updateEntityWithRequest(user, request);
            userRepository.save(user);

            return new UserInfoDto.UserInfoResponse(user);
        } else {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }
    }

    private void updateEntityWithRequest(UserEntity user, UserInfoDto.UserInfoRequest request) {
        String encPassword = null;
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            encPassword = bCryptPasswordEncoder.encode(request.getPassword());
        }

        user.update(encPassword, request.getName(), request.getPhoneNumber(), request.getRole());
    }

}