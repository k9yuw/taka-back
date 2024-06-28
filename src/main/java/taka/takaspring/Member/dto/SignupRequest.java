package taka.takaspring.Member.dto;

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
