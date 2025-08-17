package ryzendee.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ryzendee.app.common.dto.ContractorUpdateEvent;
import ryzendee.app.model.Contractor;

import java.time.LocalDateTime;

/**
 * Маппер для преобразования данных контрагента в события.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ContractorEventMapper {

    /**
     * Преобразует модель контрагента в событие обновления.
     *
     * @param contractor модель {@link Contractor}
     * @return событие {@link ContractorUpdateEvent}
     */
    @Mapping(source = "id", target = "contractorId")
    @Mapping(target = "createDate", expression = "java(mapEventDate(contractor))")
    ContractorUpdateEvent toUpdateEvent(Contractor contractor);

    /**
     * Определяет дату события: если есть дата изменения, возвращает её,
     * иначе — дату создания.
     *
     * @param contractor модель {@link Contractor}
     * @return {@link LocalDateTime} даты события
     */
    default LocalDateTime mapEventDate(Contractor contractor) {
        return contractor.getModifyDate() != null
                ? contractor.getModifyDate()
                : contractor.getCreateDate();
    }
}

