package taka.takaspring.Membership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // 입부 신청
    @PostMapping("/{orgId}/{userId}")
    public ResponseEntity<EnrollmentDto.EnrollmentResponse> enrollUserToOrg(@PathVariable Long orgId,
                                                @PathVariable Long userId) {
        EnrollmentDto.EnrollmentResponse response = enrollmentService.enrollUserToOrg(orgId, userId);
        return ResponseEntity.ok().body(response);
    }

}
