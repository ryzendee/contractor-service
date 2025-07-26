package ryzendee.app.rest.impl.base;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.rest.api.base.IndustryApi;
import ryzendee.app.service.IndustryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IndustryRestController implements IndustryApi {

    private final IndustryService industryService;

    @Override
    public List<IndustryDetails> getAll() {
        return industryService.getAll();
    }

    @Override
    public IndustryDetails getById(Integer id) {
        return industryService.getById(id);
    }

    @Override
    public IndustryDetails saveOrUpdate(IndustrySaveRequest request) {
        return industryService.saveOrUpdate(request);
    }

    @Override
    public void deleteById(Integer id) {
        industryService.deleteById(id);
    }
}
