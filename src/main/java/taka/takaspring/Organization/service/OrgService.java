package taka.takaspring.Organization.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Organization.dto.OrgDto;

import java.util.Optional;

import static io.jsonwebtoken.impl.security.EdwardsCurve.findById;

@Service
public class OrgService {

    @Autowired
    private OrgRepository orgRepository;

    @Transactional
    public OrgDto.OrgResponse registerOrg(OrgDto.OrgRequest request) {

        OrgEntity orgEntity = OrgEntity.builder().
                orgName(request.getOrgName()).
                orgAdmin(request.getOrgAdmin()).
                build();

        orgRepository.save(orgEntity);

        OrgDto.OrgResponse response = OrgDto.OrgResponse.builder().
                orgName(orgEntity.getOrgName()).
                orgAdmin(orgEntity.getOrgAdmin()).
                build();

        return response;

    }


    @Transactional(readOnly = true)
    public OrgDto.OrgResponse getOrg(Long orgId) {
        Optional<OrgEntity> orgEntity = orgRepository.findById(orgId);
        return orgEntity.orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 단체입니다. " + orgId));
    }

    @Transactional
    public OrgEntity updateOrg(Long org_id, OrgEntity orgDetails) {
        return orgRepository.save(orgEntity);
    }

    @Transactional
    public void deleteOrg(Long orgId) {
        Optional<OrgEntity> orgEntityOptional = orgRepository.findById(orgId);
        OrgEntity orgEntity = orgEntityOptional.get();
        orgRepository.delete(orgEntity);
    }
}