package ryzendee.app.dto.country;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * DTO с информацией о стране.
 * Используется для создания/обновления страны.
 *
 * @author Dmitry Ryazantsev
 */
@Builder
@Schema(description = "Запрос на создание или обновление информации о стране")
public record CountrySaveRequest(

        @Schema(description = "Идентификатор страны", example = "RU")
        String id,

        @Schema(description = "Название страны", example = "Россия")
        String name
) {
}
