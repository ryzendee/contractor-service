package ryzendee.app.dto.industry;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO с информацией об индустриальном коде.
 *
 * @author Dmitry Ryazantsev
 */
@Schema(description = "Информация об индустриальном коде")
public record IndustryDetails(

        @Schema(description = "Идентификатор индустриального кода", example = "101")
        Integer id,

        @Schema(description = "Название индустриального кода", example = "Промышленное производство")
        String name
) {
}
