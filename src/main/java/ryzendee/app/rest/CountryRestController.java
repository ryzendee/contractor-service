package ryzendee.app.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.rest.api.CountryApi;
import ryzendee.app.service.CountryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryRestController implements CountryApi {

    private final CountryService countryService;

    @Override
    public List<CountryDetails> getAll() {
        return countryService.getAll();
    }

    @Override
    public CountryDetails getById(String id) {
        return countryService.getById(id);
    }

    @Override
    public CountryDetails saveOrUpdate(CountrySaveRequest request) {
        return countryService.saveOrUpdate(request);
    }

    @Override
    public void deleteById(String id) {
        countryService.deleteById(id);
    }
}
