package ryzendee.app.dto;

import java.util.List;

/**
 * DTO с информацией об ошибках.
 * Используется для передачи ответа с сообщением ошибок.
 *
 * @author Dmitry Ryazantsev
 */
public record ErrorDetails(List<String> messages) {
}
