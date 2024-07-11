package taka.takaspring.Membership.dto;

import lombok.*;

public class EnrollmentDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EnrollmentRequest{

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class EnrollmentResponse {


        public EnrollmentResponse() {

        }
    }
}
