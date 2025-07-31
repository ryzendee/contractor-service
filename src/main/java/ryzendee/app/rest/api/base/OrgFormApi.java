package ryzendee.app.rest.api.base;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ryzendee.app.dto.orgform.OrgFormDetails;
import ryzendee.app.dto.orgform.OrgFormSaveRequest;

import java.util.List;

@RequestMapping("/org-form")
@Tag(name = "API организационных форм", description = "Операции, связанные с управлением организационными формами")
public interface OrgFormApi {

    @Operation(
            summary = "Получение списка организационных форм",
            description = "Возвращает список всех активных организационных форм",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно возвращает список организационных форм")
            }
    )
    @GetMapping("/all")
    List<OrgFormDetails> getAll();

    @Operation(
            summary = "Получение информации об организационной форме по id",
            description = "Возвращает информацию об организационной форме со всей связанной с ней информацией",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Организационная форма найдена и возвращена"),
                    @ApiResponse(responseCode = "404", description = "Организационная форма с указанным id не найдена")
            }
    )
    @GetMapping("/{id}")
    OrgFormDetails getById(@PathVariable("id") Integer id);

    @Operation(
            summary = "Создание или обновление организационной формы",
            description = "Создает новую организационную форму или полностью обновляет существующую",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Организационная форма успешно создана или обновлена"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
            }
    )
    @PutMapping("/save")
    OrgFormDetails saveOrUpdate(@Valid @RequestBody OrgFormSaveRequest request);

    @Operation(
            summary = "Удалить организационную форму по id",
            description = "Логически удаляет организационную форму, переводя её в неактивное состояние",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Организационная форма успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Организационная форма с указанным id не найдена")
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable("id") Integer id);
}
