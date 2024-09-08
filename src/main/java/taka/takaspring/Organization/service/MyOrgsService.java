package taka.takaspring.Organization.service;

import org.springframework.stereotype.Service;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.dto.OrgDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyOrgsService {

    private final MembershipRepository membershipRepository;

    public MyOrgsService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    // OrgEntity 리스트를 OrgDto.OrgResponse 리스트로 변환하여 반환
    public List<OrgDto.OrgResponse> getMyOrganizations(Long userId) {
        List<MembershipEntity> memberships = membershipRepository.findByUser_Id(userId);

        // MembershipEntity에서 OrgEntity를 추출하여 OrgDto.OrgResponse로 변환
        return memberships.stream()
                .map(membership -> {
                    OrgEntity org = membership.getOrg();
                    return new OrgDto.OrgResponse(org.getOrgName(), org.getDepartment(), org.getOrgDescription());
                })
                .collect(Collectors.toList());
    }
}
