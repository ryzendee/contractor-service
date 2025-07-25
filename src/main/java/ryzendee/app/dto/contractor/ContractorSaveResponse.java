package ryzendee.app.dto.contractor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Представляет страну.
 * Используется для передачи ответа при сохранении/обновлении контрагента
 *
 * @author Dmitry Ryazantsev
 */
@Builder
@Schema(description = "DTO ответа с информацией о сохранённом/обновлённом контрагенте")
public record ContractorSaveResponse(

        @Schema(description = "Уникальный идентификатор контрагента", example = "C123456789")
        String id,

        @Schema(description = "Идентификатор родительского контрагента", example = "P987654321")
        String parentId,

        @Schema(description = "Наименование контрагента", example = "ООО Ромашка")
        String name,

        @Schema(description = "Полное наименование контрагента", example = "Общество с ограниченной ответственностью Ромашка")
        String nameFull,

        @Schema(description = "ИНН контрагента", example = "7701234567")
        String inn,

        @Schema(description = "ОГРН контрагента", example = "1027700132195")
        String ogrn,

        @Schema(description = "Идентификатор страны", example = "RU")
        String countryId,

        @Schema(description = "Идентификатор индустриального кода", example = "123")
        Integer industryId,

        @Schema(description = "Идентификатор организационной формы", example = "10")
        Integer orgFormId
) {
}
