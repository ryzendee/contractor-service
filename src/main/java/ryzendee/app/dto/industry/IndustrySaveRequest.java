package ryzendee.app.dto.industry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * DTO с информацией об индустриальном коде.
 * Используется для создания/обновления индустриального кода.
 *
 * @author Dmitry Ryazantsev
 */
@Builder
@Schema(description = "DTO для создания или обновления индустриального кода")
public record IndustrySaveRequest(

        @Schema(description = "Идентификатор индустриального кода", example = "101")
        Integer id,

        @Schema(description = "Название индустриального кода", example = "Промышленное производство")
        String name
) {
}
