package ryzendee.app.rest.api.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ryzendee.app.dto.industry.IndustryDetails;
import ryzendee.app.dto.industry.IndustrySaveRequest;

import java.util.List;

@RequestMapping("/ui/industry")
@Tag(name = "API индустриальных кодов", description = "Операции, связанные с управлением индустриальными кодами")
public interface IndustryUiApi {

    @Operation(
            summary = "Получение списка индустриальных кодов",
            description = "Возвращает список всех активных индустриальных кодов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно возвращает список активных индустриальных кодов"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён")
            }
    )
    @GetMapping("/all")
    List<IndustryDetails> getAll();

    @Operation(
            summary = "Получение информацию о индустриальном коде по id",
            description = "Возвращает информацию о индустриальном коде со всей связанной с ним информацией",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Индустриальный код найден и возвращён"),
                    @ApiResponse(responseCode = "404", description = "Индустриальный код с указанным id не найден"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён")
            }
    )
    @GetMapping("/{id}")
    IndustryDetails getById(@PathVariable("id") Integer id);

    @Operation(
            summary = "Создание или обновление индустриального кода",
            description = "Создает новый индустриальный код или полностью обновляет существующий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Индустриальный код успешно создан или обновлен"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён")
            }
    )
    @PutMapping("/save")
    IndustryDetails saveOrUpdate(@Valid @RequestBody IndustrySaveRequest request);

    @Operation(
            summary = "Удалить индустриальный код по id",
            description = "Логически удаляет индустриальный код, переводя его в неактивное состояние",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Индустриальный код успешно удалён"),
                    @ApiResponse(responseCode = "404", description = "Индустриальный код с указанным id не найден"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён")
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable("id") Integer id);
}
