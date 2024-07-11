package taka.takaspring.Membership.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Membership.dto.MembershipDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipService(MembershipRepository userOrgRepository) {
        this.membershipRepository = userOrgRepository;
    }

    @Transactional
    public List<MembershipDto.UserByOrgResponse> getUsersByOrgId(Long organizationId) {
        List<MembershipEntity> membershipEntities = membershipRepository.findByOrgId(organizationId);
        return membershipEntities.stream()
                .map(this::convertToUserByOrgResponse)
                .collect(Collectors.toList());
    }

    private MembershipDto.UserByOrgResponse convertToUserByOrgResponse(MembershipEntity userOrgEntity) {

        MembershipDto.UserByOrgResponse response = MembershipDto.UserByOrgResponse.builder().
                name(userOrgEntity.getUser().getName()).
                major(userOrgEntity.getUser().getMajor()).
                studentNum(userOrgEntity.getUser().getStudentNum()).
                build();

        return response;
    }

    @Transactional
    public void deleteUserFromOrg(Long orgId, Long userId) {
        Optional<MembershipEntity> userOrgEntityOptional = membershipRepository.findByOrgIdAndUserId(orgId, userId);

        if (userOrgEntityOptional.isPresent()) {
            membershipRepository.delete(userOrgEntityOptional.get());
        } else {
            throw new EntityNotFoundException("단체에 해당 회원이 존재하지 않습니다.");
        }
    }
}
