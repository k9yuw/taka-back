package taka.takaspring.Organization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.dto.OrgDto;
import taka.takaspring.Organization.service.OrgService;

@PreAuthorize("hasRole('SUPER_ADMIN')")
@RestController
@RequestMapping("/api/super_admin/{org_id}")
@RequiredArgsConstructor
public class OrgController {

    private final OrgService orgService;

    // 슈퍼어드민이 단체 추가
    @PostMapping
    public ResponseEntity<OrgDto.OrgResponse> createOrg(@RequestBody @Valid OrgDto.OrgRequest request) {
        OrgDto.OrgResponse createdOrg = orgService.registerOrg(request);
        return ResponseEntity.ok().body(createdOrg);
    }

    // 슈퍼어드민이 단체 조회
    @GetMapping
    public ResponseEntity<OrgDto.OrgResponse> getOrg(@PathVariable Long orgId) {
        OrgDto.OrgResponse response = orgService.getOrg(orgId);
        return ResponseEntity.ok(response);
    }

    // 슈퍼어드민이 단체 정보 수정
    @PatchMapping
    public ResponseEntity<OrgEntity> updateOrg(@PathVariable Long orgId, @RequestBody OrgDto.OrgRequest request) {
        OrgEntity updatedOrg = orgService.updateOrg(orgId, request);
        return ResponseEntity.ok(updatedOrg);
    }

    // 슈퍼어드민이 단체 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteOrg(@PathVariable Long org_id) {
        orgService.deleteOrg(org_id);
        return ResponseEntity.noContent().build();
    }

    // 슈퍼어드민이 특정 단체의 admin 지정
    @PostMapping("/designate_admin")
    public ResponseEntity<Void> designateAdmin(@RequestParam Long userId, @RequestParam Long orgId) {
        orgService.designateAdmin(userId, orgId);
        return ResponseEntity.ok().build();
    }
}
