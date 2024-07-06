package taka.takaspring.Organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Organization.dto.UserOrgDto;
import taka.takaspring.Organization.service.UserOrgService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/{organization_id}/users")
public class UserOrgController {

    private final UserOrgService userOrgService;

    @Autowired
    public UserOrgController(UserOrgService userOrgService) {
        this.userOrgService = userOrgService;
    }

    @GetMapping
    public ResponseEntity<List<UserOrgDto.UserByOrgResponse>> getUserListByOrgId(@PathVariable("organization_id") Long orgId) {
        List<UserOrgDto.UserByOrgResponse> userList = userOrgService.getUsersByOrgId(orgId);
        return ResponseEntity.ok(userList);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUserFromOrganization(
            @PathVariable("organization_id") Long orgId,
            @PathVariable("user_id") Long userId) {
        userOrgService.deleteUserFromOrg(orgId, userId);
        return ResponseEntity.ok().build();
    }
}