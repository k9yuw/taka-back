package taka.takaspring.Member.service.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
}
