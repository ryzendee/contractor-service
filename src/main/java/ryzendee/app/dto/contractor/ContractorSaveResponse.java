package ryzendee.app.dto.contractor;

import lombok.Builder;

/**
 * Представляет страну.
 * Используется для передачи ответа при сохранении/обновлении контрагента
 *
 * @author Dmitry Ryazantsev
 */
@Builder
public record ContractorSaveResponse(

        String id,
        String parentId,
        String name,
        String nameFull,
        String inn,
        String ogrn,
        String countryId,
        Integer industryId,
        Integer orgFormId
) {
}
