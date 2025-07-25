package ryzendee.app.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ryzendee.app.dto.country.CountryDetails;
import ryzendee.app.dto.country.CountrySaveRequest;

import java.util.List;

@RequestMapping("/country")
@Tag(name = "API стран", description = "Операции, связанные с управлением странами")
public interface CountryApi {

    @Operation(
            summary = "Получение списка стран",
            description = "Возвращает список стран"
    )
    @GetMapping("/all")
    List<CountryDetails> getAll();

    @Operation(
            summary = "Получение информацию о стране по id",
            description = "Возвращает информацию о стране со всей связанной с ней информацией",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страна найдена и возвращена"),
                    @ApiResponse(responseCode = "404", description = "Страна с указанным id не найдена")
            }
    )
    @GetMapping("/{id}")
    CountryDetails getById(@PathVariable("id") String id);

    @Operation(
            summary = "Создание или обновление страны",
            description = "Создает новую страну или полностью обновляет существующую",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страна успешно создана или обновлена"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
            }
    )
    @PutMapping("/save")
    CountryDetails saveOrUpdate(@Valid @RequestBody CountrySaveRequest request);


    @Operation(
            summary = "Удалить страну по id",
            description = "Логически удаляет страну, переводя ее в неактивное состояние",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Страна успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Страна с указанным id не найдена")
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable("id") String id);
}
