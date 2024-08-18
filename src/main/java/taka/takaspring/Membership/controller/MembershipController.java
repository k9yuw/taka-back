package taka.takaspring.Membership.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.dto.MembershipDto;
import taka.takaspring.Membership.service.MembershipService;

import java.util.List;

@PreAuthorize("@accessControlService.isOrgAdmin(principal, #organization_id)")
@RestController
@RequestMapping("/api/admin/{organization_id}")
public class MembershipController {

    private final MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    // 단체의 회원 목록을 조회
    @GetMapping("/membership/users")
    public ResponseEntity<Page<MembershipDto.UserByOrgResponse>> getUserListByOrgId(
            @PathVariable("organization_id") Long orgId,
            Pageable pageable) {

        Page<MembershipDto.UserByOrgResponse> userList = membershipService.getUserListByOrgId(orgId, pageable);
        return ResponseEntity.ok().body(userList);
    }

    // 단체의 회원 목록에서 이름으로 검색
    @GetMapping("/membership/users/search")
    public ResponseEntity<Page<MembershipDto.UserByOrgResponse>> searchUsersByOrgIdAndName(
            @PathVariable("organization_id") Long orgId,
            @RequestParam("name") String name,
            Pageable pageable) {

        Page<MembershipDto.UserByOrgResponse> userList = membershipService.searchUsersByOrgIdAndName(orgId, name, pageable);
        return ResponseEntity.ok().body(userList);
    }

    // 입부 신청한 회원 목록 조회
    @GetMapping("/membership/enrolled")
    public ResponseEntity<Page<EnrollmentDto.EnrollmentIntermediateRequest>> getPendingUserList(
            @PathVariable("organization_id") Long orgId,
            Pageable pageable) {

        Page<EnrollmentDto.EnrollmentIntermediateRequest> pendingUserList = membershipService.getPendingUserList(orgId, pageable);
        return ResponseEntity.ok().body(pendingUserList);
    }

    // 단체에 회원 삭제
    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<Void> deleteUserFromOrganization(
            @PathVariable("organization_id") Long orgId,
            @PathVariable("user_id") Long userId) {

        membershipService.deleteUserFromOrg(orgId, userId);
        return ResponseEntity.ok().build();
    }

    // 입부 승인
    @PatchMapping("/membership/approve")
    public ResponseEntity<EnrollmentDto.EnrollmentResponse> approveEnrollment(
            @RequestBody EnrollmentDto.EnrollmentIntermediateRequest request) {

        EnrollmentDto.EnrollmentResponse response = membershipService.approveEnrollment(request);
        return ResponseEntity.ok().body(response);
    }

    // 입부 거절
    @PatchMapping("/membership/reject")
    public ResponseEntity<EnrollmentDto.EnrollmentResponse> rejectEnrollment(
            @RequestBody EnrollmentDto.EnrollmentIntermediateRequest request) {

        EnrollmentDto.EnrollmentResponse response = membershipService.rejectEnrollment(request);
        return ResponseEntity.ok().body(response);
    }
}
