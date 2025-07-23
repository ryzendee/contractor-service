package ryzendee.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSaveResponse;
import ryzendee.app.model.Contractor;

/**
 * Маппер для преобразования данных контрагента.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ContractorAppMapper {

    /**
     * Преобразует DTO-запрос в модель контрагента.
     *
     * @param dto объект запроса на сохранение контрагента
     * @return модель {@link Contractor}
     */
    Contractor toModel(ContractorSaveRequest dto);

    /**
     * Преобразует модель контрагента в DTO-ответ.
     *
     * @param model модель {@link Contractor}
     * @return DTO-ответ {@link ContractorSaveResponse}
     */
    ContractorSaveResponse toSaveResponse(Contractor model);
}
