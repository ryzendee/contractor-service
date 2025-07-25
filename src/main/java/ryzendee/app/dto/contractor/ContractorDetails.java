package ryzendee.app.dto.contractor;

import io.swagger.v3.oas.annotations.media.Schema;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.orgform.OrgFormDetails;

/**
 * DTO с информацией о контрагенте.
 * Содержит расширенную информацию (включает страну, индустриальный код и организационную форму).
 *
 * @author Dmitry Ryazantsev
 */
@Schema(description = "DTO с информацией о контрагенте, включая страну, индустриальный код и организационную форму")
public record ContractorDetails(
        @Schema(description = "Уникальный идентификатор контрагента", example = "C123456789")
        String id,

        @Schema(description = "Идентификатор родительского контрагента", example = "P987654321")
        String parentId,

        @Schema(description = "Краткое наименование контрагента", example = "ООО Ромашка")
        String name,

        @Schema(description = "Полное наименование контрагента", example = "Общество с ограниченной ответственностью Ромашка")
        String nameFull,

        @Schema(description = "ИНН контрагента", example = "7701234567")
        String inn,

        @Schema(description = "ОГРН контрагента", example = "1027700132195")
        String ogrn,

        @Schema(description = "Информация о стране")
        CountryDetails country,

        @Schema(description = "Информация об индустриальном коде")
        IndustryDetails industry,

        @Schema(description = "Информация об организационной форме")
        OrgFormDetails orgForm
) {
}
