package ryzendee.app.dto.contractor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * DTO с информацией о контрагенте.
 * Используется для создания/обновления контрагента.
 *
 * @author Dmitry Ryazantsev
 */
@Builder
@Schema(description = "DTO для создания или обновления контрагента")
public record ContractorSaveRequest(

        @Schema(description = "Уникальный идентификатор контрагента", example = "C123456789")
        @NotBlank(message = "id не может быть пустым")
        @Size(max = 12, message = "id не должен превышать {max} символов")
        String id,

        @Schema(description = "Идентификатор родительского контрагента", example = "P987654321")
        @Size(max = 12, message = "parentId не должен превышать {max} символов")
        String parentId,

        @Schema(description = "Наименование контрагента",
                example = "ООО Ромашка")
        @NotBlank(message = "name не может быть пустым")
        String name,

        @Schema(description = "Полное наименование контрагента", example = "ООО Завод")
        String nameFull,

        @Schema(description = "ИНН контрагента (только цифры, длина 10-12)", example = "7701234567")
        @Size(min = 10, max = 12, message = "inn должен быть длиной от {min} до {max} символов")
        @Pattern(regexp = "\\d+", message = "inn должен содержать только цифры")
        String inn,

        @Schema(description = "ОГРН контрагента (только цифры, длина 13-15)", example = "1027700132195")
        @Size(min = 13, max = 15, message = "ogrn должен быть длиной от {min} до {max} символов")
        @Pattern(regexp = "\\d+", message = "ogrn должен содержать только цифры")
        String ogrn,

        @Schema(description = "Код страны", example = "RU")
        String country,

        @Schema(description = "Идентификатор индустриального кода", example = "123")
        Integer industry,

        @Schema(description = "Идентификатор организационной формы", example = "10")
        Integer orgForm
) {
}
