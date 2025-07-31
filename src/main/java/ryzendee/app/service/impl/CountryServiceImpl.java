package ryzendee.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.mapper.CountryAppMapper;
import ryzendee.app.model.Country;
import ryzendee.app.repository.CountryRepository;
import ryzendee.app.service.CountryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryAppMapper countryAppMapper;
    private final CountryRepository countryRepository;

    @Transactional
    @Override
    public CountryDetails saveOrUpdate(CountrySaveRequest request) {
        Country country = countryAppMapper.toModel(request);
        countryRepository.save(country);
        return countryAppMapper.toDetails(country);
    }

    @Transactional(readOnly = true)
    @Override
    public CountryDetails getById(String id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country with given id does not exists"));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        if (!countryRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Failed to delete, given id does not exists");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<CountryDetails> getAll() {
        return countryRepository.findAll();
    }
}
