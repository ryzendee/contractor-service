package ryzendee.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;
import ryzendee.app.model.OrgForm;

/**
 * Маппер для преобразования данных организационной формы.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrgFormAppMapper {

    /**
     * Преобразует DTO-запрос в модель организационной формы.
     *
     * @param dto объект запроса на сохранение организационной формы
     * @return модель {@link OrgForm}
     */
    OrgForm toModel(OrgFormSaveRequest dto);

    /**
     * Преобразует модель организационной формы в DTO-детали.
     *
     * @param model модель {@link OrgForm}
     * @return DTO {@link OrgFormDetails}
     */
    OrgFormDetails toDetails(OrgForm model);
}
