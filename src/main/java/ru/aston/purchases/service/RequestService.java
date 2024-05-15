package ru.aston.purchases.service;

import ru.aston.purchases.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto saveRequest(RequestDto requestDto, Long userId);

    List<RequestDto> findAllByRequest(Long userId);

    List<RequestDto> findByAll(Long userId, Integer from, Integer size);

    RequestDto findById(Long userId, Long requestId);
}
