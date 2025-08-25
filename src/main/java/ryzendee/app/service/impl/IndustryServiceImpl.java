package ryzendee.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.exception.ResourceNotFoundException;
import ryzendee.app.mapper.IndustryAppMapper;
import ryzendee.app.model.Industry;
import ryzendee.app.repository.IndustryRepository;
import ryzendee.app.service.IndustryService;

import java.util.List;

import static ryzendee.app.constants.CacheNameConstants.INDUSTRY_METADATA;

@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {

    private final IndustryAppMapper industryAppMapper;
    private final IndustryRepository industryRepository;

    @Transactional
    @CacheEvict(value = INDUSTRY_METADATA, key = "'all'")
    @Override
    public IndustryDetails saveOrUpdate(IndustrySaveRequest request) {
        Industry industry = industryAppMapper.toModel(request);
        industryRepository.save(industry);
        return industryAppMapper.toDetails(industry);
    }

    @Transactional(readOnly = true)
    @Override
    public IndustryDetails getById(Integer id) {
        return industryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Industry with given id does not exist"));
    }

    @Transactional
    @CacheEvict(value = INDUSTRY_METADATA, key = "'all'")
    @Override
    public void deleteById(Integer id) {
        if (!industryRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Failed to delete, given id does not exist");
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(value = INDUSTRY_METADATA, key = "'all'")
    @Override
    public List<IndustryDetails> getAll() {
        return industryRepository.findAll();
    }
}
