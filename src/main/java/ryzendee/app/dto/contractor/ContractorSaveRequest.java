package ryzendee.app.dto.contractor;

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
public record ContractorSaveRequest(

        @NotBlank(message = "id не может быть пустым")
        @Size(max = 12, message = "id не должен превышать {max} символов")
        String id,

        @Size(max = 12, message = "parentId не должен превышать {max} символов")
        String parentId,

        @NotBlank(message = "name не может быть пустым")
        String name,

        String nameFull,

        @Size(min = 10, max = 12, message = "inn должен быть длиной от {min} до {max} символов")
        @Pattern(regexp = "\\d+", message = "inn должен содержать только цифры")
        String inn,

        @Size(min = 13, max = 15, message = "ogrn должен быть длиной от {min} до {max} символов")
        @Pattern(regexp = "\\d+", message = "ogrn должен содержать только цифры")
        String ogrn,
        String country,
        Integer industry,
        Integer orgForm
) {
}
