package taka.takaspring.Membership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.service.EnrollmentService;

import java.util.List;

// 유저 입장에서 단체에 가입할 때 다룰 수 있는 기능들
@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // 전체 단체 조회 - 페이지네이션 지원
    @GetMapping("/orgList")
    public ResponseEntity<Page<EnrollmentDto.EnrollableOrgResponse>> getEnrollableOrgList(Pageable pageable) {
        Page<EnrollmentDto.EnrollableOrgResponse> orgPage = enrollmentService.getEnrollableOrgList(pageable);
        return ResponseEntity.ok().body(orgPage);
    }

    // 입부 신청
    @PostMapping("/{orgId}/{userId}")
    public ResponseEntity<EnrollmentDto.EnrollmentResponse> enrollUserToOrg(@PathVariable Long orgId,
                                                @PathVariable Long userId) {
        EnrollmentDto.EnrollmentResponse response = enrollmentService.enrollUserToOrg(orgId, userId);
        return ResponseEntity.ok().body(response);
    }

}
