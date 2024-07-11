package taka.takaspring.Membership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // 입부 신청
    @PostMapping("/api/membership/{orgId}/{userId}")
    public ResponseEntity<Void> enrollUserToOrg(@RequestBody EnrollmentDto.EnrollmentRequest request,
                                                @PathVariable Long orgId,
                                                @PathVariable Long userId
                                                ) {
        enrollmentService.enrollUserToOrg(orgId, userId);
        return ResponseEntity.ok().build();
    }

    // 입부 신청한 회원 목록 조회
    @GetMapping("/admin/membership/{orgId}/enrollment")

    // 입부 승인
    @PatchMapping("/admin/membership/{membershipId}/approve")
    public ResponseEntity<EnrollmentDto.EnrollmentResponse> approveEnrollment(@PathVariable Long membershipId,
                                                                              @RequestBody EnrollmentDto.EnrollmentIntermediateRequest request) {
        EnrollmentDto.EnrollmentResponse response = enrollmentService.approveEnrollment(request);
        return ResponseEntity.ok(response);
    }

    // 입부 거절
    @PatchMapping("/admin/membership/{membershipId}/reject")
    public ResponseEntity<EnrollmentDto.EnrollmentResponse> rejectEnrollment(@PathVariable Long membershipId,
                                                                             @RequestBody EnrollmentDto.EnrollmentIntermediateRequest request) {
        EnrollmentDto.EnrollmentResponse response = enrollmentService.rejectEnrollment(request);
        return ResponseEntity.ok(response);
    }
}
