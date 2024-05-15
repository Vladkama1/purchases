package ru.aston.purchases.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aston.purchases.dto.RequestDto;
import ru.aston.purchases.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.aston.purchases.constant.Constants.X_SHARER_USER_ID;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto saveItem(@RequestHeader(X_SHARER_USER_ID) Long userId, @Valid @RequestBody RequestDto requestDto) {
        log.info("Получен запрос Post, на создание запроса.");
        return requestService.saveRequest(requestDto, userId);
    }

    @GetMapping
    public List<RequestDto> findAllByRequest(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("Получен запрос GET, на получения всех запросов.");
        List<RequestDto> requestDtoList = requestService.findAllByRequest(userId);
        log.info("Получен ответ, список запросов, размер: {}", requestDtoList.size());
        return requestDtoList;
    }

    @GetMapping("/all")
    public List<RequestDto> findByAll(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                      @RequestParam(defaultValue = "0") @Min(value = 0) Integer from,
                                      @RequestParam(defaultValue = "20") @Positive Integer size) {
        log.info("Получен запрос GET, на получения всех запросов.");
        List<RequestDto> requestDtoList = requestService.findByAll(userId, from, size);
        log.info("Получен ответ, список запросов, размер: {}", requestDtoList.size());
        return requestDtoList;
    }

    @GetMapping("/{requestId}")
    public RequestDto findById(@RequestHeader(X_SHARER_USER_ID) Long userId,
                               @PathVariable Long requestId) {
        log.info("Получен запрос GET, на получения запроса.");
        return requestService.findById(userId, requestId);
    }
}
