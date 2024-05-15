package ru.aston.purchases.service;

import ru.aston.purchases.dto.CommentDto;
import ru.aston.purchases.dto.CommentOutDto;
import ru.aston.purchases.dto.ItemDto;
import ru.aston.purchases.dto.ItemOutDto;

import java.util.List;

public interface ItemService {
    ItemOutDto findById(Long userId, Long itemId);

    List<ItemOutDto> getAllItemsOwner(Long ownerId, Integer from, Integer size);

    ItemOutDto saveItem(ItemDto itemDto, Long userId);

    ItemOutDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    List<ItemDto> searchBy(String text, Integer from, Integer size);

    CommentOutDto addComment(Long userId, Long itemId, CommentDto requestDto);
}
