package ryzendee.app.rest.impl.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.rest.api.ui.CountryUiApi;
import ryzendee.app.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/ui/country")
@RequiredArgsConstructor
public class CountryUiRestController implements CountryUiApi {

    private final CountryService countryService;

    @PreAuthorize("@contractorAccessRules.canReadReferenceInfo(authentication)")
    @Override
    public List<CountryDetails> getAll() {
        return countryService.getAll();
    }

    @PreAuthorize("@contractorAccessRules.canReadReferenceInfo(authentication)")
    @Override
    public CountryDetails getById(String id) {
        return countryService.getById(id);
    }

    @PreAuthorize("@contractorAccessRules.canEditReferenceInfo(authentication)")
    @Override
    public CountryDetails saveOrUpdate(CountrySaveRequest request) {
        return countryService.saveOrUpdate(request);
    }

    @PreAuthorize("@contractorAccessRules.canEditReferenceInfo(authentication)")
    @Override
    public void deleteById(String id) {
        countryService.deleteById(id);
    }
}