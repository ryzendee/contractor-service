package ryzendee.app.dto.contractor;

import lombok.Builder;

/**
 * DTO с параметрами фильтрации.
 * Используется для поиска контрагентов по заданным параметрам.
 *
 * @author Dmitry Ryazantsev
 */
@Builder
public record ContractorSearchFilter(

        String contractorId,
        String parentId,
        String contractorSearch,
        String country,
        Integer industry,
        String orgForm,
        int page,
        int size

) {
}
