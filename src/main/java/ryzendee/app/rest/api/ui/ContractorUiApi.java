package ryzendee.app.rest.api.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ryzendee.app.dto.contractor.ContractorDetails;
import ryzendee.app.dto.contractor.ContractorSaveRequest;
import ryzendee.app.dto.contractor.ContractorSaveResponse;
import ryzendee.app.dto.contractor.ContractorSearchFilter;

import java.util.List;

@RequestMapping("/ui/contractor")
@Tag(name = "UI API контрагентов", description = "Операции, связанные с управлением контрагентами (UI)")
public interface ContractorUiApi {

    @Operation(
            summary = "Создание или обновление контрагента",
            description = "Создает нового контрагента, либо полностью обновляет существующего",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное создание или обновление контрагента"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён")
            }
    )
    @PutMapping("/save")
    ContractorSaveResponse saveOrUpdateContractor(@Valid @RequestBody ContractorSaveRequest requestDto);

    @Operation(
            summary = "Получение информации о контрагенте по id",
            description = "Возвращает информацию о контрагенте со всей связанной с ним информацией",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Контрагент найден и возвращён"),
                    @ApiResponse(responseCode = "404", description = "Контрагент с указанным id не найден"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён")
            }
    )
    @GetMapping("/{id}")
    ContractorDetails getById(@PathVariable("id") String id);

    @Operation(
            summary = "Удаление контрагента по id",
            description = "Логически удаляет контрагента, переводя его в неактивное состояние",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Контрагент успешно удалён"),
                    @ApiResponse(responseCode = "404", description = "Контрагент с указанным id не найден"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён")
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable("id") String id);

    @Operation(
            summary = "Поиск активных контрагентов",
            description = "Ищет контрагентов с возможностью фильтрации данных по полям",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Контрагенты найдены"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён")
            }
    )
    @PostMapping("/search")
    List<ContractorDetails> searchActiveContractors(@RequestBody ContractorSearchFilter filterDto);
}
