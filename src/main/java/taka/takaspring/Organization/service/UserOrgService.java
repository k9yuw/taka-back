package taka.takaspring.Organization.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Organization.db.UserOrgEntity;
import taka.takaspring.Organization.db.UserOrgRepository;
import taka.takaspring.Organization.dto.UserOrgDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserOrgService {

    private final UserOrgRepository userOrgRepository;
    private final UserRepository userRepository;
    private final OrgRepository orgRepository;

    @Autowired
    public UserOrgService(UserOrgRepository userOrgRepository, UserRepository userRepository, OrgRepository orgRepository) {
        this.userOrgRepository = userOrgRepository;
        this.userRepository = userRepository;
        this.orgRepository = orgRepository;
    }

    @Transactional
    public List<UserOrgDto.UserByOrgResponse> getUsersByOrgId(Long organizationId) {
        List<UserOrgEntity> userOrgEntities = userOrgRepository.findByOrgId(organizationId);
        return userOrgEntities.stream()
                .map(this::convertToUserByOrgResponse)
                .collect(Collectors.toList());
    }

    private UserOrgDto.UserByOrgResponse convertToUserByOrgResponse(UserOrgEntity userOrgEntity) {

        UserOrgDto.UserByOrgResponse response = UserOrgDto.UserByOrgResponse.builder().
                name(userOrgEntity.getUser().getName()).
                major(userOrgEntity.getUser().getMajor()).
                studentNum(userOrgEntity.getUser().getStudentNum()).
                build();

        return response;
    }

    @Transactional
    public void deleteUserFromOrg(Long orgId, Long userId) {
        Optional<UserOrgEntity> userOrgEntityOptional = userOrgRepository.findByOrgIdAndUserId(orgId, userId);

        if (userOrgEntityOptional.isPresent()) {
            userOrgRepository.delete(userOrgEntityOptional.get());
        } else {
            throw new EntityNotFoundException("User not found in this organization");
        }
    }
}
