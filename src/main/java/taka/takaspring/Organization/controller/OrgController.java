package taka.takaspring.Organization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.dto.OrgDto;
import taka.takaspring.Organization.service.OrgService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class OrgController {

    private final OrgService orgService;

    @PostMapping
    public ResponseEntity<OrgDto.OrgResponse> createOrg(@RequestBody @Valid OrgDto.OrgRequest request) {
        OrgDto.OrgResponse createdOrg = orgService.registerOrg(request);
        return ResponseEntity.ok().body(createdOrg);
    }

    @GetMapping("/{org_id}")
    public ResponseEntity<OrgEntity> getOrg(@PathVariable Long org_id) {
        OrgEntity orgEntity = orgService.getOrg(org_id);
        return ResponseEntity.ok(orgEntity);
    }

    @PatchMapping("/{org_id}")
    public ResponseEntity<OrgEntity> updateOrg(@PathVariable Long orgId, @RequestBody OrgDto.OrgRequest request) {
        OrgEntity updatedOrg = orgService.updateOrg(orgId, request);
        return ResponseEntity.ok(updatedOrg);
    }

    @DeleteMapping("/{org_id}")
    public ResponseEntity<Void> deleteOrg(@PathVariable Long org_id) {
        orgService.deleteOrg(org_id);
        return ResponseEntity.noContent().build();
    }
}
