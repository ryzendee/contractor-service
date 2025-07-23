package ryzendee.app.repository;

import org.springframework.stereotype.Repository;
import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.contractor.ContractorSearchFilter;
import ryzendee.app.model.Contractor;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Contractor.
 *
 * @author Dmitry Ryazantsev
 */
@Repository
public interface ContractorRepository {

    /**
     * Сохраняет или обновляет информацию о контрагенте
     * Если идентификатора нет БД - выполняется вставка
     * Если идентификатор присутствует в БД — обновление существующей записи
     *
     * @param contractor объект контрагент для сохранения
     * @return сохранённый объект Contractor
     */
    Contractor save(Contractor contractor);

    /**
     * Ищет подробную информацию о контрагенте по его идентификатору.
     *
     * @param id идентификатор контрагента в виде строки
     * @return Optional с данными контрагента, включая связанные данные
     */
    Optional<ContractorDetails> findDetailsById(String id);

    /**
     * Логически удаляет контрагента по идентификатору
     *
     * @param id идентификатор контрагента
     * @return true, если удаление прошло успешно, иначе false
     */
    boolean deleteById(String id);

    /**
     * Получает список подробных данных контрагентов по заданному фильтру поиска.
     *
     * @param filter объект с параметрами поиска контрагента
     * @return список ContractorDetails, удовлетворяющих фильтру
     */
    List<ContractorDetails> findAllDetailsByFilter(ContractorSearchFilter filter);
}
