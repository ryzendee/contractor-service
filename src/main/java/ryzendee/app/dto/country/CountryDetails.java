package ryzendee.app.dto.country;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO с информацией о стране.
 *
 * @author Dmitry Ryazantsev
 */
@Schema(description = "Информация о стране")
public record CountryDetails(

        @Schema(description = "Идентификатор страны", example = "RU")
        String id,

        @Schema(description = "Название страны", example = "Россия")
        String name
) {
}
