package ryzendee.app.service;

import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSaveResponse;
import ryzendee.app.dto.contractor.ContractorSearchFilter;

import java.util.List;

/**
 * Сервис для работы с контрагентами.
 *
 * @author Dmitry Ryazantsev
 */
public interface ContractorService {

    /**
     * Сохраняет нового или обновляет существующего контрагента.
     *
     * @param request DTO с данными контрагента для сохранения
     * @return ContractorSaveResponse с информацией о сохраненном контрагенте
     */
    ContractorSaveResponse saveOrUpdateContractor(ContractorSaveRequest request);

    /**
     * Получает контрагента по идентификатору со всей связанной информацией.
     *
     * @param id уникальный идентификатор контрагента
     * @return ContractorDetails с полной информацией о контрагенте
     */
    ContractorDetails getById(String id);

    /**
     * Выполняет логическое удаление контрагента по идентификатору.
     * Устанавливает флаг is_active = false.
     *
     * @param id уникальный идентификатор контрагента
     */
    void deleteById(String id);

    /**
     * Выполняет поиск активных контрагентов с возможностью фильтрации.
     * Возвращает только контрагентов с is_active = true.
     *
     * @param filter объект с параметрами фильтрации
     * @return список ContractorDetails с полной информацией о контрагентах
     */
    List<ContractorDetails> searchActiveContractors(ContractorSearchFilter filter);
}
