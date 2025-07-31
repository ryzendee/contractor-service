package ryzendee.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.mapper.OrgFormAppMapper;
import ryzendee.app.model.OrgForm;
import ryzendee.app.repository.OrgFormRepository;
import ryzendee.app.service.OrgFormService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrgFormServiceImpl implements OrgFormService {

    private final OrgFormAppMapper orgFormAppMapper;
    private final OrgFormRepository orgFormRepository;

    @Transactional
    @Override
    public OrgFormDetails saveOrUpdate(OrgFormSaveRequest request) {
        OrgForm orgForm = orgFormAppMapper.toModel(request);
        orgFormRepository.save(orgForm);
        return orgFormAppMapper.toDetails(orgForm);
    }

    @Transactional(readOnly = true)
    @Override
    public OrgFormDetails getById(Integer id) {
        return orgFormRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrgForm with given id does not exist"));
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        if (!orgFormRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Failed to delete, given id does not exist");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrgFormDetails> getAll() {
        return orgFormRepository.findAll();
    }
}
