package ryzendee.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.country.CountrySaveRequest;
import ryzendee.app.model.Country;

/**
 * Маппер для преобразования данных страны.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CountryAppMapper {

    /**
     * Преобразует DTO-запрос в модель страны.
     *
     * @param request объект запроса на сохранение страны
     * @return модель {@link Country}
     */
    Country toModel(CountrySaveRequest request);

    /**
     * Преобразует модель страны в DTO-детали.
     *
     * @param country модель {@link Country}
     * @return DTO {@link CountryDetails}
     */
    CountryDetails toDetails(Country country);
}
