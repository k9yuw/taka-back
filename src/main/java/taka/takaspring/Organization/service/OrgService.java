package taka.takaspring.Organization.service;


import org.springframework.stereotype.Service;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Organization.dto.OrgDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrgService {

    private final OrgRepository orgRepository;

    public OrgService(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    public List<OrgDto.OrgResponse> getAllOrgs() {
        List<OrgEntity> orgEntities = orgRepository.findAll();
        return orgEntities.stream()
                .map(org -> OrgDto.OrgResponse.builder()
                        .orgName(org.getOrgName())
                        .department(org.getDepartment())
                        .orgDescription(org.getOrgDescription())
                        .build())
                .collect(Collectors.toList());
    }
}