package ryzendee.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.industry.IndustrySaveRequest;
import ryzendee.app.model.Industry;

/**
 * Маппер для преобразования данных индустриального кода.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IndustryAppMapper {

    /**
     * Преобразует DTO-запрос в модель индустриального кода.
     *
     * @param dto объект запроса на сохранение индустриального кода
     * @return модель {@link Industry}
     */
    Industry toModel(IndustrySaveRequest dto);

    /**
     * Преобразует модель индустриального кода в DTO-детали.
     *
     * @param model модель {@link Industry}
     * @return DTO {@link IndustryDetails}
     */
    IndustryDetails toDetails(Industry model);
}
