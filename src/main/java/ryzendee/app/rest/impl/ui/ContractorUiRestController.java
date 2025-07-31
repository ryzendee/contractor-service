package ryzendee.app.rest.impl.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSaveResponse;
import ryzendee.app.dto.contractor.ContractorSearchFilter;
import ryzendee.app.rest.api.ui.ContractorUiApi;
import ryzendee.app.service.ContractorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContractorUiRestController implements ContractorUiApi {

    private final ContractorService contractorService;

    @PreAuthorize("@contractorAccessRules.canEditContractors(authentication)")
    @Override
    public ContractorSaveResponse saveOrUpdateContractor(ContractorSaveRequest requestDto) {
        return contractorService.saveOrUpdateContractor(requestDto);
    }

    @PreAuthorize("@contractorAccessRules.canReadContractors(authentication)")
    @Override
    public ContractorDetails getById(String id) {
        return contractorService.getById(id);
    }

    @PreAuthorize("@contractorAccessRules.canEditContractors(authentication)")
    @Override
    public void deleteById(String id) {
        contractorService.deleteById(id);
    }

    @PreAuthorize("@contractorAccessRules.canSearchContractors(authentication, #filterDto.country())")
    @Override
    public List<ContractorDetails> searchActiveContractors(ContractorSearchFilter filterDto) {
        return contractorService.searchActiveContractors(filterDto);
    }
}
