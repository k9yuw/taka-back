package taka.takaspring.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Member.service.dto.UserInfoDto;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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
            UserEntity updatedUser = updateEntityBuild(user, request);
            userRepository.save(updatedUser);
            return new UserInfoDto.UserInfoResponse(updatedUser);
        } else {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }
    }

    private UserEntity updateEntityBuild(UserEntity user, UserInfoDto.UserInfoRequest request) {
        UserEntity.UserEntityBuilder builder = user.toBuilder();

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            String encPassword = bCryptPasswordEncoder.encode(request.getPassword());
            builder.password(encPassword);
        }
        if (request.getName() != null && !request.getName().isEmpty()) {
            builder.name(request.getName());
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
            builder.phoneNumber(request.getPhoneNumber());
        }
        if (request.getRole() != null) {
            builder.role(request.getRole());
        }

        return builder.build();
    }
    }

