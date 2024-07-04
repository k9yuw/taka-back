package taka.takaspring.Organization.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;

import java.util.Optional;

@Service
public class OrgService {

    @Autowired
    private OrgRepository orgRepository;

    @Transactional
    public OrgEntity createOrg() {
        return orgRepository.save(orgEntity);
    }

    @Transactional(readOnly = true)
    public OrgEntity getOrg(Long org_id) {
        Optional<OrgEntity> orgEntity = orgRepository.findById(org_id);
        return orgEntity.orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 단체입니다. " + org_id));
    }

    @Transactional
    public OrgEntity updateOrg(Long org_id, OrgEntity orgDetails) {
        return orgRepository.save(orgEntity);
    }

    @Transactional
    public void deleteOrg(Long org_id) {
        OrgEntity orgEntity = getOrg(org_id);
        orgRepository.delete(orgEntity);
    }
}