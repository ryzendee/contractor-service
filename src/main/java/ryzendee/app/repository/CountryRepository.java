package ryzendee.app.repository;

import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.model.Country;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Country.
 *
 * @author Dmitry Ryazantsev
 */
public interface CountryRepository {

    /**
     * Сохраняет или обновляет информацию о стране.
     * Если идентификатора нет БД - выполняется вставка
     * Если идентификатор присутствует в БД — обновление существующей записи
     *
     * @param country объект страны для сохранения
     * @return сохранённый объект Country с заполненным идентификатором
     */
    Country save(Country country);

    /**
     * Ищет подробную информацию о стране по её идентификатору.
     *
     * @param id идентификатор страны в виде строки
     * @return Optional с данными страны, если найдена
     */
    Optional<CountryDetails> findById(String id);

    /**
     * Логически удаляет страну по идентификатору
     *
     * @param id идентификатор страны
     * @return true, если удаление прошло успешно, иначе false
     */
    boolean deleteById(String id);

    /**
     * Получает список подробных данных по всем активным странам.
     *
     * @return список CountryDetails всех стран
     */
    List<CountryDetails> findAll();
}
