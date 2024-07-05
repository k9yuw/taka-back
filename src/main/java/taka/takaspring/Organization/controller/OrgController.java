package taka.takaspring.Organization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Member.service.UserInfoService;
import taka.takaspring.Member.service.dto.UserInfoDto;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.dto.OrgRegiDto;
import taka.takaspring.Organization.service.OrgService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class OrgController {

    private final OrgService orgService;

    @PostMapping
    public ResponseEntity<OrgEntity> createOrg(@RequestBody @Valid OrgRegiDto.OrgRegiRequest request) {
        OrgRegiDto.OrgRegiResponse createdOrg = orgService.createOrg(request);
        return ResponseEntity.ok(createdOrg);
    }

    @GetMapping("/{org_id}")
    public ResponseEntity<OrgEntity> getOrg(@PathVariable Long org_id) {
        OrgEntity orgEntity = orgService.getOrg(org_id);
        return ResponseEntity.ok(orgEntity);
    }

    @PatchMapping("/{org_id}")
    public ResponseEntity<OrgEntity> updateOrg(@PathVariable Long org_id, @RequestBody OrgEntity orgEntity) {
        OrgEntity updatedOrg = orgService.updateOrg(org_id, orgEntity);
        return ResponseEntity.ok(updatedOrg);
    }

    @DeleteMapping("/{org_id}")
    public ResponseEntity<Void> deleteOrg(@PathVariable Long org_id) {
        orgService.deleteOrg(org_id);
        return ResponseEntity.noContent().build();
    }
}
