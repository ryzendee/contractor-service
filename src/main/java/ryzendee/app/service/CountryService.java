package ryzendee.app.service;

import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.country.CountrySaveRequest;

import java.util.List;

/**
 * Сервис для работы со странами.
 *
 * @author Dmitry Ryazantsev
 */
public interface CountryService {

    /**
     * Сохраняет новую или обновляет существующую страну.
     *
     * @param request DTO с данными страны для сохранения
     * @return CountryDetails с информацией о сохраненной стране
     */
    CountryDetails saveOrUpdate(CountrySaveRequest request);

    /**
     * Получает страну по идентификатору со всей связанной информацией.
     *
     * @param id уникальный идентификатор контрагента
     * @return CountryDetails с полной информацией о стране
     */
    CountryDetails getById(String id);

    /**
     * Выполняет логическое удаление страны по идентификатору.
     * Устанавливает флаг is_active = false.
     *
     * @param id уникальный идентификатор страны
     */
    void deleteById(String id);

    /**
     * Выполняет поиск активных стран .
     * Возвращает только страны с is_active = true.
     *
     * @return список CountryDetails с полной информацией о странах
     */
    List<CountryDetails> getAll();

}
