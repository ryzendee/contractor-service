package ryzendee.app.dto.orgform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * DTO с информацией об организационной форме.
 * Используется для создания/обновления организационной формы.
 *
 * @author Dmitry Ryazantsev
 */
@Builder
@Schema(description = "DTO для создания или обновления организационной формы")
public record OrgFormSaveRequest(

        @Schema(description = "Идентификатор организационной формы", example = "1")
        Integer id,

        @Schema(description = "Название организационной формы", example = "ООО")
        String name
) {
}
