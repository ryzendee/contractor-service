package ryzendee.app.dto.industry;

import lombok.Builder;

/**
 * DTO с информацией об индустриальном коде.
 * Используется для создания/обновления индустриального кода.
 *
 * @author Dmitry Ryazantsev
 */
@Builder
public record IndustrySaveRequest(Integer id, String name) {
}
