package ryzendee.app.service;

import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.industry.IndustrySaveRequest;

import java.util.List;

/**
 * Сервис для работы с индустриальными кодами.
 *
 * @author Dmitry Ryazantsev
 */
public interface IndustryService {

    /**
     * Сохраняет новый или обновляет существующий индустриальный код.
     *
     * @param request DTO с данными индустриального кода для сохранения
     * @return IndustryDetails с информацией о сохраненной индустриальном коде
     */
    IndustryDetails saveOrUpdate(IndustrySaveRequest request);

    /**
     * Получает индустриальный код по идентификатору со всей связанной информацией.
     * Возвращает только коды с is_active = true.

     * @param id уникальный идентификатор индустриального кода
     * @return IndustryDetails с полной информацией о индустриальном коде
     */
    IndustryDetails getById(Integer id);

    /**
     * Выполняет логическое удаление страны по идентификатору.
     * Устанавливает флаг is_active = false.
     *
     * @param id уникальный идентификатор страны
     */
    void deleteById(Integer id);

    /**
     * Выполняет поиск активных индустриальных кодов.
     * Возвращает только коды с is_active = true.
     *
     * @return список IndustryDetails с полной информацией о индустриалььных кодах
     */
    List<IndustryDetails> getAll();
}
