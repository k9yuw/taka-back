package taka.takaspring.Organization.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Membership.exception.MembershipNotFoundException;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Organization.dto.OrgDto;

import java.util.Optional;

@PreAuthorize("hasRole('SUPER_ADMIN')")
@Service
public class OrgService {

    @Autowired
    private final OrgRepository orgRepository;
    private final MembershipRepository membershipRepository;

    public OrgService(OrgRepository orgRepository, MembershipRepository membershipRepository){
        this.orgRepository = orgRepository;
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    public OrgDto.OrgResponse registerOrg(OrgDto.OrgRequest request) {

        OrgEntity orgEntity = OrgEntity.builder().
                orgName(request.getOrgName()).
                department(request.getDepartment()).
                orgDescription(request.getOrgDescription()).
                build();

        orgRepository.save(orgEntity);

        OrgDto.OrgResponse response = OrgDto.OrgResponse.builder().
                orgName(orgEntity.getOrgName()).
                department(request.getDepartment()).
                orgDescription(request.getOrgDescription()).
                build();

        return response;

    }


    @Transactional
    public OrgDto.OrgResponse getOrg(Long orgId) {
        OrgEntity orgEntity = orgRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 단체입니다."));

        OrgDto.OrgResponse response = OrgDto.OrgResponse.builder().
                orgName(orgEntity.getOrgName()).
                department(orgEntity.getDepartment()).
                orgDescription(orgEntity.getOrgDescription()).
                build();

        return response;
    }

    @Transactional
    public OrgEntity updateOrg(Long orgId, OrgDto.OrgRequest request) {
        OrgEntity updateOrg = orgRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 단체입니다."));
        updateOrg.updateFields(request.getDepartment(), request.getOrgDescription());
        return orgRepository.save(updateOrg);
    }

    @Transactional
    public void deleteOrg(Long orgId) {
        Optional<OrgEntity> orgEntityOptional = orgRepository.findById(orgId);
        OrgEntity orgEntity = orgEntityOptional.get();
        orgRepository.delete(orgEntity);
    }

    // 슈퍼어드민이 특정 단체의 관리자 지정
    @Transactional
    public void designateAdmin(Long userId, Long orgId) {

        MembershipEntity membership = membershipRepository.findByOrgIdAndUserId(orgId, userId)
                .orElseThrow(() -> new MembershipNotFoundException("존재하지 않는 멤버십 - user id: " + userId + " org id: " + orgId));

        // Admin 권한 부여
        membership.designateAdmin(true);
        membershipRepository.save(membership);
    }
}