package taka.takaspring.Membership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Membership.dto.MembershipDto;
import taka.takaspring.Membership.service.MembershipService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/{organization_id}/users")
public class MembershipController {

    private final MembershipService userOrgService;

    @Autowired
    public MembershipController(MembershipService userOrgService) {
        this.userOrgService = userOrgService;
    }

    // 특정 단체의 회원 목록을 조회
    @GetMapping
    public ResponseEntity<List<MembershipDto.UserByOrgResponse>> getUserListByOrgId(@PathVariable("organization_id") Long orgId) {
        List<MembershipDto.UserByOrgResponse> userList = userOrgService.getUsersByOrgId(orgId);
        return ResponseEntity.ok(userList);
    }

    // 단체에 회원 삭제
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUserFromOrganization(
            @PathVariable("organization_id") Long orgId,
            @PathVariable("user_id") Long userId) {
        userOrgService.deleteUserFromOrg(orgId, userId);
        return ResponseEntity.ok().build();
    }

}