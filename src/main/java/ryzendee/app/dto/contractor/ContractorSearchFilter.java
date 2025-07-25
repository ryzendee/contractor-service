package ryzendee.app.dto.contractor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * DTO с параметрами фильтрации.
 * Используется для поиска контрагентов по заданным параметрам.
 *
 * @author Dmitry Ryazantsev
 */

@Builder
@Schema(description = "Параметры фильтрации для поиска контрагентов")
public record ContractorSearchFilter(

        @Schema(description = "Идентификатор контрагента", example = "C123456789")
        String contractorId,

        @Schema(description = "Идентификатор родительского контрагента", example = "P987654321")
        String parentId,

        @Schema(description = "Строка поиска по наименованию или другим атрибутам", example = "Ромашка")
        String contractorSearch,

        @Schema(description = "Идентификатор страны", example = "RU")
        String country,

        @Schema(description = "Идентификатор индустрии", example = "123")
        Integer industry,

        @Schema(description = "Идентификатор организационной формы", example = "10")
        String orgForm,

        @Schema(description = "Номер страницы для пагинации", example = "0")
        int page,

        @Schema(description = "Размер страницы для пагинации", example = "10")
        int size

) {
}
