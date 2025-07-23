package ryzendee.app.dto.contractor;

import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.orgform.OrgFormDetails;

/**
 * DTO с информацией о контрагенте.
 * Содержит расширенную информацию (включает страну, индустриальный код и организационную форму).
 *
 * @author Dmitry Ryazantsev
 */
public record ContractorDetails(
        String id,
        String parentId,
        String name,
        String nameFull,
        String inn,
        String ogrn,
        CountryDetails country,
        IndustryDetails industry,
        OrgFormDetails orgForm
) {
}
