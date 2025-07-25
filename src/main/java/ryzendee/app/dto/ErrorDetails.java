package ryzendee.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * DTO с информацией об ошибках.
 * Используется для передачи ответа с сообщением ошибок.
 *
 * @author Dmitry Ryazantsev
 */
@Schema(description = "DTO с информацией об ошибках")
public record ErrorDetails(
        @Schema(description = "Список сообщений об ошибках", example = "[\"Ошибка 1\", \"Ошибка 2\"]")
        List<String> messages
) {
}
