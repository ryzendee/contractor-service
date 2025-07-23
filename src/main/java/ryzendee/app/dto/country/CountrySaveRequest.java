package ryzendee.app.dto.country;

import lombok.Builder;

/**
 * DTO с информацией о стране.
 * Используется для создания/обновления страны.
 *
 * @author Dmitry Ryazantsev
 */
@Builder
public record CountrySaveRequest(String id, String name) {
}
