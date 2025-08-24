package ryzendee.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ryzendee.app.common.dto.ContractorUpdateEvent;
import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSaveResponse;
import ryzendee.app.dto.contractor.ContractorSearchFilter;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.mapper.ContractorAppMapper;
import ryzendee.app.model.Contractor;
import ryzendee.app.mapper.ContractorEventMapper;
import ryzendee.app.service.helper.OutboxHelper;
import ryzendee.app.repository.ContractorRepository;
import ryzendee.app.service.ContractorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final ContractorAppMapper contractorAppMapper;
    private final ContractorEventMapper contractorEventMapper;
    private final OutboxHelper outboxHelper;

    @Transactional
    @Override
    public ContractorSaveResponse saveOrUpdateContractor(ContractorSaveRequest request) {
        Contractor contractor = contractorAppMapper.toModel(request);
        contractorRepository.save(contractor);

        ContractorUpdateEvent event = contractorEventMapper.toUpdateEvent(contractor);
        outboxHelper.save(event);

        return contractorAppMapper.toSaveResponse(contractor);
    }

    @Transactional(readOnly = true)
    @Override
    public ContractorDetails getById(String id) {
        return contractorRepository.findDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contractor with given id does not exists"));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        if (!contractorRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Failed to delete, contractor with given id does not exists");
        };
    }

    @Transactional(readOnly = true)
    @Override
    public List<ContractorDetails> searchActiveContractors(ContractorSearchFilter filter) {
        return contractorRepository.findAllDetailsByFilter(filter);
    }
}
