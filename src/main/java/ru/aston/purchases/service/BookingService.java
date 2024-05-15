package ru.aston.purchases.service;


import ru.aston.purchases.dto.BookingDto;
import ru.aston.purchases.dto.BookingOutDto;

import java.util.List;

public interface BookingService {
    BookingOutDto findById(Long userId, Long bookingId);

    BookingOutDto saveBooking(Long userId, BookingDto bookingDto);

    BookingOutDto updateBooking(Long userId, Long id, Boolean approved);

    List<BookingOutDto> findByBookerAndState(Long userId, String state, Integer from, Integer size);

    List<BookingOutDto> findByOwnerAndState(Long userId, String state, Integer from, Integer size);
}
