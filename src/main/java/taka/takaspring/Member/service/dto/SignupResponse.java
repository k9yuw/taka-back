package taka.takaspring.Member.service.dto;

import lombok.Data;

@Data
public class SignupResponse {
    private String userId;
    private String name;
    private String email;
}
