package ryzendee.app.service;

import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;

import java.util.List;

/**
 * Сервис для работы с организационными формами.
 *
 * @author Dmitry Ryazantsev
 */
public interface OrgFormService {

    /**
     * Сохраняет новую или обновляет существующую организационную форму.
     *
     * @param request DTO с данными для сохранения
     * @return IndustryDetails с информацией о сохраненной организационной форме
     */
    OrgFormDetails saveOrUpdate(OrgFormSaveRequest request);

    /**
     * Получает организационную форму по идентификатору со всей связанной информацией.
     * Возвращает только коды с is_active = true.

     * @param id уникальный идентификатор организационной формы
     * @return IndustryDetails с полной информацией об организационной форме
     */
    OrgFormDetails getById(Integer id);

    /**
     * Выполняет логическое удаление организационной формы по идентификатору.
     * Устанавливает флаг is_active = false.
     *
     * @param id уникальный идентификатор организационной формы
     */
    void deleteById(Integer id);

    /**
     * Выполняет поиск активных организационные формы
     * Возвращает только формы с is_active = true
     *
     * @return список IndustryDetails с информацией об организационных формах
     */
    List<OrgFormDetails> getAll();

}
