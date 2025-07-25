package ryzendee.app.repository;

import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.model.OrgForm;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью OrgForm.
 *
 * @author Dmitry Ryazantsev
 */
public interface OrgFormRepository {

    /**
     * Сохраняет или обновляет запись организационной формы.
     * Если у объекта отсутствует идентификатор, выполняется вставка с генерацией ID.
     * Если идентификатор присутствует — обновляется существующая запись.
     *
     * @param orgForm объект организационной формы для сохранения
     * @return сохранённый объект OrgForm с заполненным идентификатором
     */
    OrgForm save(OrgForm orgForm);

    /**
     * Находит подробную информацию об организационной форме по её идентификатору.
     *
     * @param id идентификатор организационной формы в виде строки
     * @return Optional с данными организационной формы, если найдена
     */
    Optional<OrgFormDetails> findById(Integer id);

    /**
     * Логически удаляет организационную форму по идентификатору.
     *
     * @param id идентификатор организационной формы
     * @return true, если удаление прошло успешно, иначе false
     */
    boolean deleteById(Integer id);

    /**
     * Возвращает список всех активных организационных форм.
     *
     * @return список OrgFormDetails активных организационных форм
     */
    List<OrgFormDetails> findAll();

}
