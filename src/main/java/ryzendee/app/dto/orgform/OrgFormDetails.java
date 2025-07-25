package ryzendee.app.dto.orgform;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO с информацией об организационной форме.
 *
 * @author Dmitry Ryazantsev
 */
@Schema(description = "DTO с информацией об организационной форме")
public record OrgFormDetails(

        @Schema(description = "Идентификатор организационной формы", example = "1")
        Integer id,

        @Schema(description = "Название организационной формы", example = "ООО")
        String name
) {
}
