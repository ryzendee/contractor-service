package ryzendee.app.rest.impl.base;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSaveResponse;
import ryzendee.app.dto.contractor.ContractorSearchFilter;
import ryzendee.app.rest.api.base.ContractorApi;
import ryzendee.app.service.impl.ContractorServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContractorRestController implements ContractorApi {

    private final ContractorServiceImpl contractorService;

    @Override
    public ContractorSaveResponse saveOrUpdateContractor(ContractorSaveRequest requestDto) {
        return contractorService.saveOrUpdateContractor(requestDto);
    }

    @Override
    public ContractorDetails getById(String id) {
        return contractorService.getById(id);
    }

    @Override
    public void deleteById(String id) {
        contractorService.deleteById(id);
    }

    @Override
    public List<ContractorDetails> searchActiveContractors(ContractorSearchFilter filterDto) {
        return contractorService.searchActiveContractors(filterDto);
    }
}
