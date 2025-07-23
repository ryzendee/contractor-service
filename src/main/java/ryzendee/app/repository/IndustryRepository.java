package ryzendee.app.repository;

import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.model.Industry;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Industry.
 *
 * @author Dmitry Ryazantsev
 */
public interface IndustryRepository {

    /**
     * Сохраняет или обновляет запись индустриального кода.
     * Если у объекта отсутствует идентификатор, выполняется вставка с генерацией ID.
     * Если идентификатор присутствует — обновляется существующая запись.
     *
     * @param industry объект индустриального кода для сохранения
     * @return сохранённый объект Industry с заполненным идентификатором
     */
    Industry save(Industry industry);

    /**
     * Находит подробную информацию об индустриальном коде по его идентификатору.
     *
     * @param id идентификатор индустриального кода в виде строки
     * @return Optional с данными индустриального кода, если найден
     */
    Optional<IndustryDetails> findById(Integer id);

    /**
     * Логически удаляет индустриальный код по идентификатору
     *
     * @param id идентификатор индустриального кода
     * @return true, если удаление прошло успешно, иначе false
     */
    boolean deleteById(Integer id);

    /**
     * Возвращает список всех активных индустриальных кодов.
     *
     * @return список IndustryDetails активных индустриальных кодов
     */
    List<IndustryDetails> findAll();

}
