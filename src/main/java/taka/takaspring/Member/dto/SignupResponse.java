package taka.takaspring.Member.dto;

import lombok.Data;

@Data
public class SignupResponse {
    private Long id;
    private String userId;
    private String name;
    private String email;
}
