package ryzendee.app.dto.orgform;

import lombok.Builder;

/**
 * DTO с информацией об организационной форме.
 * Используется для создания/обновления организационной формы.
 *
 * @author Dmitry Ryazantsev
 */
@Builder
public record OrgFormSaveRequest(Integer id, String name) {
}
