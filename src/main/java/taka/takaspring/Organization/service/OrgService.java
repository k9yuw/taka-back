package taka.takaspring.Organization.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Organization.dto.OrgDto;

import java.util.Optional;

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


    @Transactional
    public OrgDto.OrgResponse getOrg(Long orgId) {
        OrgEntity orgEntity = orgRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 단체입니다."));

        OrgDto.OrgResponse response = OrgDto.OrgResponse.builder().
                orgName(orgEntity.getOrgName()).
                orgAdmin(orgEntity.getOrgAdmin()).
                build();

        return response;

    }

    @Transactional
    public OrgEntity updateOrg(Long orgId, OrgDto.OrgRequest request) {

        OrgEntity updateOrg = orgRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 단체입니다."));

        updateOrg.updateFields(request.getOrgName(), request.getOrgAdmin());

        return orgRepository.save(updateOrg);
    }

    @Transactional
    public void deleteOrg(Long orgId) {
        Optional<OrgEntity> orgEntityOptional = orgRepository.findById(orgId);
        OrgEntity orgEntity = orgEntityOptional.get();
        orgRepository.delete(orgEntity);
    }
}